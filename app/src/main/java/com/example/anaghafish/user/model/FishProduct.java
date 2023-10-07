package com.example.anaghafish.user.model;

public class FishProduct {
    private String name;
    private double price;
    private int imageResourceId;

    //picasso and glide -> (url -> img)
    public FishProduct(String name, double price, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    // Getter methods for name, price, and imageResourceId
    // ...
}
