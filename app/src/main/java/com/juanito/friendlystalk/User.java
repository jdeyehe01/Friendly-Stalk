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
    private List<String> demands;
    private String dateAdresse;
    private Boolean invisible;
    private String adresse;

    public User(){
    }

    public User(String lastName, String firstName, String email,String pseudo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.pseudo = pseudo;
        this.friendsPseudo = new ArrayList<>();
        this.friendsPseudo.add("");
        this.demands = new ArrayList<>();
        this.demands.add("test");
        this.dateAdresse = "";
        this.invisible = false;
        this.adresse = "";
    }

    public User(String id,String lastName, String firstName, String email,String pseudo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.pseudo = pseudo;
        this.friendsPseudo = new ArrayList<>();
        this.friendsPseudo.add("");
        this.demands = new ArrayList<>();
        this.demands.add("");
        this.dateAdresse = "";
        this.invisible = false;
        this.id = id;
        this.adresse = "";
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

    public List<String> getDemands() {
        return demands;
    }

    public void setFriendsPseudo(List<String> friendsPseudo) {
        this.friendsPseudo = friendsPseudo;
    }

    public void setDemands(List<String> demands) {
        this.demands = demands;
    }

    public String getDateAdresse() {
        return dateAdresse;
    }

    public void setDateAdresse(String dateAdresse) {
        this.dateAdresse = dateAdresse;
    }

    public Boolean getInvisible() {
        return invisible;
    }

    public void setInvisible(Boolean invisible) {
        this.invisible = invisible;
    }
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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
                ", demands=" + demands +
                ", dateAdresse='" + dateAdresse + '\'' +
                ", invisible=" + invisible +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
