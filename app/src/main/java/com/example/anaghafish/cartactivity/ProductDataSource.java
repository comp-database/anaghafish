package com.example.anaghafish.cartactivity;

import com.example.anaghafish.R;

import java.util.ArrayList;
import java.util.List;

public class ProductDataSource {
    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        // Add raw fish products to the list
        products.add(new Product(R.drawable.fish1, "Salmon Sashimi", 10.99, 0));
        products.add(new Product(R.drawable.fish2, "Tuna Sashimi", 12.99, 0));
        products.add(new Product(R.drawable.fish3, "Salmon Sashimi", 10.99, 0));
        products.add(new Product(R.drawable.fish4, "Sashimi", 13.99, 0));
        products.add(new Product(R.drawable.raw, "Salmon", 15.99, 0));
        products.add(new Product(R.drawable.dish, "dish", 11.99, 0));
        // Add more products as needed

        return products;
    }
}
