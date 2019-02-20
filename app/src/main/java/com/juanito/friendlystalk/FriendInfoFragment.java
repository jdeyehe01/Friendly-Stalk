package com.juanito.friendlystalk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class FriendInfoFragment extends Fragment {
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("User");
    private TextView pseudo;
    private TextView lastName;
    private TextView firstName;


    public FriendInfoFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_info, container, true);

        return view;
    }


    public void changeText(final View v , String pseudoFriends){


        Query query = db.orderByChild("pseudo").equalTo(pseudoFriends);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    User u = d.getValue(User.class);
                    if(u!=null) {

                        pseudo = v.findViewById(R.id.friendPseudo);
                        lastName = v.findViewById(R.id.friendLastName);

                        firstName = v.findViewById(R.id.friendFirstName);
                        String valPseudo = u.getPseudo()==null?"Pseudo non renseigne ":"Pseudo : "+u.getPseudo();
                        String valLastName = u.getLastName()==null?"Nom non renseigne ":"Pseudo : "+u.getLastName();
                        String valFirstName = u.getFirstName()==null?"Prenom non renseigne ":"Pseudo : "+u.getFirstName();
                        pseudo.setText(valPseudo);
                        lastName.setText(valLastName);
                        firstName.setText(valFirstName);

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    }

