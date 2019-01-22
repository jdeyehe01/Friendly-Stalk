package com.juanito.friendlystalk;

import java.util.List;

public interface IUserDao {
    void insert(User user);
    List<User> getAll();
    User getUserByEmail(String email);
    User getUserByPseudo(String pseudo);
    List<User> getListFriendOneUser(String pseudoUser);

}
