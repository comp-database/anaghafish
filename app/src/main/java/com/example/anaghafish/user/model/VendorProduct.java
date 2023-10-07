package com.example.anaghafish.user.model;
import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class VendorProduct {
    private String imageUrl;
    private String productName;
    private String vendorName;
    private String price;
    private String phoneNumber; // New field for phone number

    public VendorProduct() {
        // Default constructor required for Firebase
    }

    public VendorProduct(String imageUrl, String productName, String vendorName, String price) {
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.vendorName = vendorName;
        this.price = price;
         // Initialize phone number
    }

    // Getter and setter methods for each field

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
