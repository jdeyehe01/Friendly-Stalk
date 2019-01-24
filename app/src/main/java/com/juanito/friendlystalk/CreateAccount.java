package com.juanito.friendlystalk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {
    private EditText fName;
    private EditText lName;
    private EditText email;
    private EditText pw;
    private Button btnCreateUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();



        btnCreateUser = (Button) findViewById(R.id.btnCreateAccount);
        fName = (EditText) findViewById(R.id.editTextFName);
        lName = (EditText) findViewById(R.id.editTextName);
        email = (EditText) findViewById(R.id.editTextEmailUser);
        pw = (EditText) findViewById(R.id.editTextPwUser);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });



    }

    private void signUp(){
        //String firstName = fName.getText().toString();
        //String lastName = lName.getText().toString();
        String emailTxt = email.getText().toString();
        String pwTxt = pw.getText().toString();

        Log.i("OPTION", emailTxt);
        Log.i("OPTION", pwTxt);



        if(!emailTxt.isEmpty() && !pwTxt.isEmpty()){
            mAuth.createUserWithEmailAndPassword(emailTxt,pwTxt)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                              /* FirebaseUser user = mAuth.getCurrentUser();
                                Intent newUser = new Intent(CreateAccount.this,Home.class);
                                newUser.putExtra("currentUser",user);*/
                                FirebaseUser user = mAuth.getCurrentUser();

                                startActivity(new Intent(CreateAccount.this,Home.class));

                            }else{
                                Log.i("TAG","error");
                            }
                        }
                    });

        }
    }
}
