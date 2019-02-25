package com.juanito.friendlystalk;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyInformationActivity extends AppCompatActivity {

    private Button btnUpdate;
    private EditText firstName;
    private EditText lastName;
    private EditText address;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        firstName = (EditText) findViewById(R.id.editTextFName);
        lastName = (EditText) findViewById(R.id.editTextLastName);
        address = (EditText) findViewById(R.id.editTextAddress);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userUpdate();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initField();
    }

    private void initField() {

        Query query = db.orderByChild("email").equalTo(currentUser.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    User u = d.getValue(User.class);
                    if(u!=null) {
                       firstName.setText(u.getFirstName());
                       lastName.setText(u.getLastName());
                       address.setText(u.getAdresse());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private  void userUpdate(){
            final Map<String, Object> userUpdates = new HashMap<>();
            userUpdates.put("adresse", address.getText().toString());
            userUpdates.put("firstName",firstName.getText().toString());
            userUpdates.put("lastName" , lastName.getText().toString());
        Query query = db.orderByChild("email").equalTo(currentUser.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    User u = d.getValue(User.class);
                    if(u!=null) {
                        db.child(u.getId()).updateChildren(userUpdates);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(MyInformationActivity.this,"Vos informations on été modifié", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MyInformationActivity.this,HomeActivity.class));

    }


}
