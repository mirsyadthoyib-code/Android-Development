package com.example.faceteknik.API;

public class Comment {

    private int id;
    private String username;
    private String date;
    private String comment;

    public Comment(int id, String username, String date, String comment) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }
}
