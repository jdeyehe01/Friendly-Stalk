package com.juanito.friendlystalk;

import java.util.ArrayList;
import java.util.List;

public class User{
    private String lastName;
    private String firstName;
    private String email;
    private String id;
    private String pseudo;
    private List<String> friendsPseudo;


    public User(){
    }

    public User(String lastName, String firstName, String email,String pseudo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.pseudo = pseudo;
        this.friendsPseudo = new ArrayList<>();
        this.friendsPseudo.add("Boby");
        this.friendsPseudo.add("Bob");
        this.friendsPseudo.add("Jo'");
       // friendsPseudo.add(new User());
    }

    public User(String id,String lastName, String firstName, String email,String pseudo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.pseudo = pseudo;
        this.friendsPseudo = new ArrayList<>();
        this.friendsPseudo.add("Boby");
        this.friendsPseudo.add("Bob");
        this.friendsPseudo.add("Jo'");
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public List<String> getFriendsPseudo() {
        return friendsPseudo;
    }

    public void setFriendsPseudo(List<String> friendsPseudo) {
        this.friendsPseudo = friendsPseudo;
    }

    @Override
    public String toString() {
        return "User{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", friendsPseudo=" + friendsPseudo +
                '}';
    }
}
