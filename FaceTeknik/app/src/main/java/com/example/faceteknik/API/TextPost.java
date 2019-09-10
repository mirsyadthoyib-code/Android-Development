package com.example.faceteknik.API;

public class TextPost extends Post {

    private String textContent;
    private String imageContent;

    public TextPost(int id, String username, String date, String textContent, String imageContent) {
        super(id, username, date);

        this.textContent = textContent;
        this.imageContent = imageContent;
    }

    public String getTextContent() {
        return textContent;
    }
    public String getImageContent() {
        return imageContent;
    }
}
