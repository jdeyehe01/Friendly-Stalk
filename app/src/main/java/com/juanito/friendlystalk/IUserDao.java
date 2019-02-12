package com.juanito.friendlystalk;

import java.util.ArrayList;

public interface IUserDao {

    void insert(User user);
    public ArrayList<User> getAll();
    User getUserByPseudo(String pseudo);
    public ArrayList<User> getFriends();
    public User findByEmail(String email);

}
