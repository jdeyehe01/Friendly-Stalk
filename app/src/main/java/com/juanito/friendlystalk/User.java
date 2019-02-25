package com.juanito.friendlystalk;

import java.util.ArrayList;
import java.util.List;

public class User{
    private String lastName;
    private String firstName;
    private String email;
    private String id;
    private String pseudo;
    private String lastLocation;
    private List<String> friendsPseudo;
    private String dateAdresse;
    private Boolean invisible;


    public User(){
    }

    public User(String lastName, String firstName, String email,String pseudo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.pseudo = pseudo;
        this.lastLocation = "";
        this.friendsPseudo = new ArrayList<>();
        this.friendsPseudo.add("");
        this.dateAdresse = "";
    }

    public User(String id,String lastName, String firstName, String email,String pseudo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.pseudo = pseudo;
        this.lastLocation = "";
        this.friendsPseudo = new ArrayList<>();
        this.friendsPseudo.add("");
        this.dateAdresse = "";

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

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public String getDateAdresse() {
        return dateAdresse;
    }

    public void setDateAdresse(String dateAdresse) {
        this.dateAdresse = dateAdresse;
    }

    @Override
    public String toString() {
        return "User{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", lastLocation='" + lastLocation + '\'' +
                ", friendsPseudo=" + friendsPseudo +
                ", dateAdresse='" + dateAdresse + '\'' +
                '}';
    }
}
