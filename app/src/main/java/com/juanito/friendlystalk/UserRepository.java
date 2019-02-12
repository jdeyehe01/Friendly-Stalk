package com.juanito.friendlystalk;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserRepository implements IUserDao {


    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("User");
   // private String id = database.push().getKey();
    private ArrayList<User> res ;


    public UserRepository(){

        res = new ArrayList<User>();
    }

    @Override
    public void insert(User user) {
      /*  user.setId(id);
        database.child(id).setValue(user);        */

      new InsertUserAsync().execute(user);
    }

    @Override
    public ArrayList<User> getAll() {
        database.addListenerForSingleValueEvent(valueEventListener);

        if(res.isEmpty()){
            return null;
        }
        return res;
    }


    @Override
    public User getUserByPseudo(String pseudo) {
        Query query = database.orderByChild("pseudo").equalTo(pseudo);
        query.addListenerForSingleValueEvent(valueEventListener);
        if(res.isEmpty()){
            return null;
        }
        return res.get(0);
    }
    
    @Override
    public ArrayList<User> getFriends() {
        
        Query query = database.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Query friends = query.orderByChild("friends");
        friends.addValueEventListener(valueEventListenerList);

        if(res.isEmpty()){
            return null;
        }
        return res;
    }

    @Override
    public User findByEmail(String email) {
        Query query = database.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(valueEventListener);

        if(res.isEmpty()){
            return null;
        }
        return res.get(0);
    }



    static class InsertUserAsync extends AsyncTask<User, Void, Void>{
        private DatabaseReference database;
        private String id;

        public InsertUserAsync()
        {
            this.database = FirebaseDatabase.getInstance().getReference("User");

        }

        @Override
        protected Void doInBackground(User... users) {
            for(User user : users)
            {
                this.id = this.database.push().getKey();
                user.setId(id);
                database.child(id).setValue(user);

            }

            return null;
        }
    }



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
    
    
    ValueEventListener valueEventListenerList = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            res.clear();

            if (dataSnapshot.exists()) {
                res = dataSnapshot.getValue(ArrayList.class);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
