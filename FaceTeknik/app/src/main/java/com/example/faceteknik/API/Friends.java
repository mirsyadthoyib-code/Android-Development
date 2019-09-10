package com.example.faceteknik.API;

public class Friends {

    private int id;
    private String username, biodata;

    public Friends(int id, String username, String biodata) {
        this.id = id;
        this.username = username;
        this.biodata = biodata;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getBiodata() {
        return biodata;
    }
}
