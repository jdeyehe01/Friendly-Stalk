package com.juanito.friendlystalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();


       // btnLogout = findViewById(R.id.btnLogout);

        /*btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home.this,MainActivity.class));
                Toast.makeText(Home.this,"Vous avez été dectonnecté",Toast.LENGTH_SHORT).show();


            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(Home.this,"Bienvenue "+ currentUser.getEmail(),Toast.LENGTH_SHORT).show();

    }
}


