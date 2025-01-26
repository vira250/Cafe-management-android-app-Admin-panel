package com.example.cafeadmin.Model;

import java.util.List;

public class BuffetModel {

    String id, category, buffet_name, description, buffet_price;

    private List<String> imageUrls;
    private boolean show;

    public BuffetModel() {

    }

    public BuffetModel(String id, String category, String buffet_name, String description, String buffet_price, List<String> imageUrls, boolean show) {
        this.id = id;
        this.category = category;
        this.buffet_name = buffet_name;
        this.description = description;
        this.buffet_price = buffet_price;
        this.imageUrls = imageUrls;
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

    public String getBuffet_name() {
        return buffet_name;
    }

    public void setBuffet_name(String buffet_name) {
        this.buffet_name = buffet_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuffet_price() {
        return buffet_price;
    }

    public void setBuffet_price(String buffet_price) {
        this.buffet_price = buffet_price;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
