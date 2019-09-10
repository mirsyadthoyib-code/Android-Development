package com.example.faceteknik.API;

public class User {

    private int id;
    private String username;
    private String fullname;
    private String password;
    private String email;
    private String tanggalLahir;
    private String bio;

    public User(int id, String username, String fullname, String password,
                String email, String tanggalLahir, String bio) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.email = email;
        this.tanggalLahir = tanggalLahir;
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public String getBio() {
        return bio;
    }
}
