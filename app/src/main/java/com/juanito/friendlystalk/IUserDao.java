package com.juanito.friendlystalk;

import java.util.List;

interface IUserDao {
    void insert(User user);
    public List<User> getAll();
    User getUserByEmail(String email);
    User getUserByPseudo(String pseudo);
    public List<User> getListFriendOneUser(String pseudoUser);

}
