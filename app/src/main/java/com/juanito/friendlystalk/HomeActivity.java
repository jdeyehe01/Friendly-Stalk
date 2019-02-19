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
    private Button btnListFriends;
    private Button btnInvitations;
    private Button btnUpdateInformation;

    //private User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        btnLogout = findViewById(R.id.btnLogout);
        btnInvitations = findViewById(R.id.btnMyInvitations);
        btnListFriends = findViewById(R.id.btnMyFriends);
        btnUpdateInformation = findViewById(R.id.btnUpdateInformation);

        btnLogout.setOnClickListener(logoutListener);
        btnListFriends.setOnClickListener(listFriends);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(HomeActivity.this, "Bienvenue " + currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
    }


    View.OnClickListener logoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            //ancienne methode
            //startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            //nouvelle methode (ajouts de FLAG pour eviter de revenir en arriere alors qu'on est bien logout
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(HomeActivity.this, "Vous êtes deconnecté", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener listFriends = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(HomeActivity.this,MyFriendsActivity.class));
        }
    };

}


