package com.juanito.friendlystalk;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserRepository implements IUserDao {
    private DatabaseReference myDatabaseReference;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("User");
    String id = database.getKey();



    @Override
    public void insert(User user) {
        database.child(id).setValue(user);


    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserByPseudo(String pseudo) {
        return null;
    }

    @Override
    public List<User> getListFriendOneUser(String pseudoUser) {
        return null;
    }
}
