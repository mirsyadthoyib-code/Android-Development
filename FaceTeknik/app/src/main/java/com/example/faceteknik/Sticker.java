package com.example.faceteknik;

public enum Sticker {

    SATU("Sticker1", R.drawable.sticker1),
    DUA("Sticker2", R.drawable.sticker2),
    TIGA("Sticker3", R.drawable.sticker3),
    EMPAT("Sticker4", R.drawable.sticker4);

    private String path;
    private int id;

    Sticker(String path, int id) {
        this.path = path;
        this.id = id;
    }

    public String toString()
    {
        return path;
    }

    public int getID() { return id; }
}
