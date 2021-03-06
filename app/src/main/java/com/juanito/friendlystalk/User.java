package com.juanito.friendlystalk;

import java.util.ArrayList;

public class User{
    private String lastName;
    private String firstName;
    private String email;
    private String id;
    private String pseudo;
    private ArrayList<String> friendsPseudo;
    private String token;


    public User(){
    }

    public User(String lastName, String firstName, String email,String pseudo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.id ="0" ;
        this.pseudo = pseudo;
        this.token = "";
        this.friendsPseudo = new ArrayList<>();
        this.friendsPseudo.add("");
       // friendsPseudo.add(new User());
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

    public ArrayList<String> getFriends() {
        return friendsPseudo;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friendsPseudo = friends;
    }

    public void addFriend(String userPseudo){
        friendsPseudo.add(userPseudo);
    }

    @Override
    public String toString() {
        return "User{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", friends=" + friendsPseudo +
                '}';
    }
}
