package com.juanito.friendlystalk;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//a chaque lancement de l'appli on est dans cette classe, on sait si le user est login ou non
public class HomeManager extends Application {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    public void onCreate() {
        super.onCreate();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //si le user est connecté
        if(currentUser != null){
            startActivity(new Intent(HomeManager.this, HomeActivity.class));
        }
    }
}
