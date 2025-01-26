package com.example.cafeadmin.Model;

public class CategoryModel {
    private String id, category;
    private String image;
    private boolean show;

    public CategoryModel() {


    }

    public CategoryModel(String id, String category, String image, boolean show) {
        this.id = id;
        this.category = category;
        this.image = image;
        this.show = show;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
