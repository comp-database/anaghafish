package com.example.anaghafish.cartactivity;

public class Product {
    private int imageResource;
    private String productName;
    private double price;
    private int quantity;

    public Product(int imageResource, String productName, double price, int quantity) {
        this.imageResource = imageResource;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

