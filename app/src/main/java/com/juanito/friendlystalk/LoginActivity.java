package com.juanito.friendlystalk;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("User");

    public static final int REQUEST_CODE = 1;
    private Button btnCreateAccount;
    private Button btnSignInWithEmail;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    //Connection with email/password
    private EditText emailET;
    private EditText pwET;

    //Connection with google account
    private GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnSignInWithEmail = (Button) findViewById(R.id.btnLoginEmail);

        emailET = (EditText) findViewById(R.id.editTextMail);
        pwET = (EditText) findViewById(R.id.editTextPw);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        btnSignInWithEmail.setOnClickListener(signWithEmail);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(signWithGoogleAcc);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult();
            if (account != null) {
                signInWithGoogleAcccount(account);
            }
        }

    }

    private void signIn() {
        String email = emailET.getText().toString();
        String pw = pwET.getText().toString();
        final Intent intent = new Intent(this, HomeActivity.class);


        mAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(mAuth.getCurrentUser().isEmailVerified()){
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Veuiller confirmer votre adresse mail  svp \n Un mail à l'adresse " + mAuth.getCurrentUser().getEmail() + " vous a été envoyé ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "L'authentification a échoué ! ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }


    private void signInWithGoogleAcccount(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Query query = db.orderByChild("email").equalTo(acct.getEmail());

                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User u = dataSnapshot.getValue(User.class);
                                    if (u==null){
                                        u = new User(acct.getFamilyName(),acct.getGivenName(),acct.getEmail(),acct.getDisplayName());
                                        String id = db.push().getKey();
                                        u.setId(id);
                                        db.child(id).setValue(u);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(LoginActivity.this,"Non Trouver ",Toast.LENGTH_SHORT).show();

                                }
                            });




                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Connexion refusé, veuillez confirmer votre adresse mail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    View.OnClickListener signWithEmail = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (emailET.getText().toString().isEmpty()) {
                emailET.setError("Mail obligatoire");
                return;
            }
            if (pwET.getText().toString().isEmpty()) {
                pwET.setError("Passeword obligatoire");
                return;
            }
            signIn();
        }
    };

    View.OnClickListener signWithGoogleAcc = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, REQUEST_CODE);
        }

    };
}


