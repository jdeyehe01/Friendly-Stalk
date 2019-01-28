package com.juanito.friendlystalk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    private Button btnCreateAccount;
    private Button btnSignInWithEmail;

    private FirebaseAuth mAuth;

    //Connection with email/password
    private EditText emailET;
    private EditText pwET;

    //Connection with google account
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "SignInActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth.getInstance().signOut();

        mAuth = FirebaseAuth.getInstance();

        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnSignInWithEmail = (Button) findViewById(R.id.btnLoginEmail);

        emailET = (EditText) findViewById(R.id.editTextMail);
        pwET = (EditText) findViewById(R.id.editTextPw);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        btnSignInWithEmail.setOnClickListener(new View.OnClickListener() {
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
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateAccount.class));
            }
        });

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, REQUEST_CODE);
            }
        });

    }
/*
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            startActivity(new Intent(MainActivity.this,Home.class));
        }else{
            Toast.makeText(MainActivity.this,"Veuillez vous connecter svp ",Toast.LENGTH_SHORT).show();

        }
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
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
        final Intent intent = new Intent(this, Home.class);


        mAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    intent.putExtra("userCurrent", currentUser);

                    if(mAuth.getCurrentUser().isEmailVerified()){
                        startActivity(intent);

                    }else{
                        Toast.makeText(MainActivity.this, "Veuiller confirmer votre adresse mail  svp \n Un mail à l'adresse " + mAuth.getCurrentUser().getEmail() + " vous a été envoyé ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "L'authentification a échoué ! ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }


    private void signInWithGoogleAcccount(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "signInWithCredential:success");
                            startActivity(new Intent(MainActivity.this, Home.class));
                        } else {
                            Log.i(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });

    }
}


