package com.juanito.friendlystalk;

import java.util.List;

interface IUserDao {
    void insert(User user);
    public List<User> getAll();
    User getUserByPseudo(String pseudo);
    public List<User> getListFriendOneUser(String pseudoUser);
    public User findByEmail(String email);

}
