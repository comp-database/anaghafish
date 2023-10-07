package com.example.anaghafish.user.userui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafish.R;
import com.example.anaghafish.user.SelectedItemsViewModel;
import com.example.anaghafish.user.adapter.ProductAdapter;
import com.example.anaghafish.user.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryFragment extends Fragment {
    private ProductAdapter adapter;
    private List<Product> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        SelectedItemsViewModel selectedItemsViewModel = new ViewModelProvider(this).get(SelectedItemsViewModel.class);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productsRef = database.getReference("VendorProductlist");

        // Initialize your RecyclerView and its adapter
        RecyclerView recyclerView = view.findViewById(R.id.categoryRecyclerView);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(productList , selectedItemsViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot vendorSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot productSnapshot : vendorSnapshot.getChildren()) {
                        // Assuming that each product node has fields like "product_name", "vendor_name", "image_url", "price"
                        Product product = new Product();
                        product.setProductName(productSnapshot.child("product_name").getValue(String.class));
                        Log.d("name",productSnapshot.child("product_name").getValue(String.class));
                        product.setVendorName(productSnapshot.child("vendor_name").getValue(String.class));
                        product.setImageUrl(productSnapshot.child("image_url").getValue(String.class));
                        product.setPrice(productSnapshot.child("price").getValue(String.class));
                        productList.add(product);
                    }
                }
                // Now, you can use the productList to populate your UI or RecyclerView
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Log.e("FirebaseError", "Firebase Data Error: " + databaseError.getMessage());
            }
        });

        return view;
    }
}

