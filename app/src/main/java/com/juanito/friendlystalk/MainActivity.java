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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    private Button btnCreateAccount;
    private Button btnSignInWithEmail;
    private FirebaseAuth mAuth;
    private EditText emailET;
    private EditText pwET;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnSignInWithEmail = (Button) findViewById(R.id.btnLoginEmail);
        emailET = (EditText) findViewById(R.id.editTextMail) ;
        pwET = (EditText)findViewById(R.id.editTextPw);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        btnSignInWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailET.setError("Mail obligatoire");
                pwET.setError("Passeword obligatoire");
                signIn();
            }
        });
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CreateAccount.class));
            }
        });


    }




    private void signIn(){
        String email = emailET.getText().toString();
        String pw = pwET.getText().toString();
        final Intent intent = new Intent(this, Home.class);


        mAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    intent.putExtra("userCurrent", currentUser);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"L'authentification a échoué ! ",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }





}


