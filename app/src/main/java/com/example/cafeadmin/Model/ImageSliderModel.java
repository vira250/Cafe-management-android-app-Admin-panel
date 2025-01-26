package com.example.cafeadmin.Model;

public class ImageSliderModel {
    private String id;
    private String image;
    private boolean active;

    public ImageSliderModel() {
    }

    public ImageSliderModel(String id, String image, boolean active) {
        this.id = id;
        this.image = image;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
