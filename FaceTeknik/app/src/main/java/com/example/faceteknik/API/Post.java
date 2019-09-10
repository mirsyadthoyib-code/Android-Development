package com.example.faceteknik.API;

public class Post {

    private int id;
    private String username;
    private String date;

    public Post(int id, String username, String date) {
        this.id = id;
        this.username = username;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }
}
