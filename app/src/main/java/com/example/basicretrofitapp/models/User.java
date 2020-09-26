package com.example.basicretrofitapp.models;

public class User {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String birth;
    private String password;

    public User() {}

    public User(String email, String firstname, String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }

    public User withBirth(String birth) {
        this.birth = birth;
        return this;
    }

    public User withId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birth='" + birth + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
