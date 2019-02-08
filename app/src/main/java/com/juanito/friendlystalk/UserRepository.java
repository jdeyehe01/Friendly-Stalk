package com.juanito.friendlystalk;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserDao {

    private DatabaseReference database ;
    private String id;
    private List<User> res ;

    public UserRepository(){
       //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       database = FirebaseDatabase.getInstance().getReference("User");
       id = database.push().getKey();
       res = new ArrayList<User>();
    }




    @Override
    public void insert(User user) {
        user.setId(id);
        database.child(id).setValue(user);
    }

    @Override
    public List<User> getAll() {
        database.addListenerForSingleValueEvent(valueEventListener);

        return res;
    }

    public User getTest(String t){
        Query query = FirebaseDatabase.getInstance().getReference("User").orderByChild("email").equalTo(t);
        query.addListenerForSingleValueEvent(valueEventListener);
        if(res.isEmpty()){
            return null;
        }
        return res.get(0);
    }

    @Override
    public User getUserByPseudo(String pseudo) {

        res.clear();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");

        Query query = ref.orderByChild("pseudo").equalTo(pseudo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        User u = snapshot.getValue(User.class);
                        if(u != null){
                            res.add(u);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if(res.isEmpty()){
            return null;
        }
        return res.get(0);
    }

    @Override
    public List<User> getListFriendOneUser(String pseudoUser) {

        res.clear();


        List<String> pseudos = new ArrayList<String>();



        return null;
    }

    @Override
    public User findByEmail(String email) {

        res.clear();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");

        Query query = ref.orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        User u = snapshot.getValue(User.class);
                        if(u != null){
                            res.add(u);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if(res.isEmpty()){
            return null;
        }
        return res.get(0);    }


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                res.clear();

                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                       User user = snapshot.getValue(User.class);
                       res.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
}
