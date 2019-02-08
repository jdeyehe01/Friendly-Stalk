package com.juanito.friendlystalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button btnLogout;
    private UserRepository repo = new UserRepository();
    //private User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        //User currentSignIn = new User("Toto","Titi",currentUser.getEmail(),currentUser.getUid(),"");

        //repo.insert(currentSignIn);

       btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                Toast.makeText(HomeActivity.this,"Vous êtes dectonnectés",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(HomeActivity.this,"Bienvenue "+ currentUser.getDisplayName(),Toast.LENGTH_SHORT).show();
    }
}


