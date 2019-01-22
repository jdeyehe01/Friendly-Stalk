package com.juanito.friendlystalk;

public class User {
    private String lastName;
    private String firstName;
    private String email;
    private String id;
    private String pseudo;

    public User(String lastName, String firstName, String email, String pseudo) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.pseudo = pseudo;
    }

    public User(){
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


}
