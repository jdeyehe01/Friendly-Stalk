package com.juanito.friendlystalk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FriendInfoFragment extends Fragment {
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference("User");
    private TextView pseudo;
    private TextView lastName;
    private TextView firstName;
    private Button btnDeleteFriend;


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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnDeleteFriend = getView().findViewById(R.id.btnDeleteFriend);
        pseudo = getView().findViewById(R.id.friendPseudo);

        btnDeleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFriend(pseudo.getText().toString());
            }
        });


        btnDeleteFriend.setVisibility(View.INVISIBLE);
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
                        btnDeleteFriend = v.findViewById(R.id.btnDeleteFriend);

                        btnDeleteFriend.setVisibility(View.VISIBLE);

                        String valPseudo = u.getPseudo()==null?"Pseudo non renseigne ":"Pseudo : "+u.getPseudo();
                        String valLastName = u.getLastName()==null?"Nom non renseigne ":"Nom : "+u.getLastName();
                        String valFirstName = u.getFirstName()==null?"Prenom non renseigne ":"Prenom : "+u.getFirstName();
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

    public void deleteFriend(final String pseudoFriend){
        Query query = db.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    User u = d.getValue(User.class);
                    if(u!=null) {

                       List<String> friendsPseudo =  u.getFriendsPseudo();
                       String userPseudo = pseudoFriend.split(" : ")[1];
                       friendsPseudo.remove(userPseudo);
                       updateUser(u.getId(),friendsPseudo);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void updateUser(String idUser, List<String> friends){
        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("friendsPseudo", friends);

        db.child(idUser).updateChildren(userUpdates);

        pseudo = getView().findViewById(R.id.friendPseudo);
        lastName = getView().findViewById(R.id.friendLastName);
        firstName = getView().findViewById(R.id.friendFirstName);



        pseudo.setText("");
        lastName.setText("");
        firstName.setText("");

    }



    }

