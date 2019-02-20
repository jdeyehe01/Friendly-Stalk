package com.juanito.friendlystalk;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsActivity extends AppCompatActivity {
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("User");
    private RecyclerView recyclerView;
    private MyUserAdapter userAdapter;
    static List<String> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friends);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        userList = new ArrayList<String>();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        displayFriends(currentUser.getEmail());



    }

    private void displayFriends(String idUser){
        Query query = db.orderByChild("email").equalTo(idUser);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   // Iterable<DataSnapshot> friends = dataSnapshot.child("friends").getChildren();

                    for(DataSnapshot d : dataSnapshot.getChildren()){
                        User u = d.getValue(User.class);
                        userList = u.getFriendsPseudo();

                    }

                userAdapter = new MyUserAdapter(userList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}