package com.example.faceteknik.API;

public class ImagePost extends Post{

    private String imageContent;

    public ImagePost(int id, String username, String date,
                     String imageContent) {
        super(id, username, date);

        this.imageContent = imageContent;
    }

    public String getImageContent() {
        return imageContent;
    }
}
