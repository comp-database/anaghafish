package com.example.anaghafish.cartactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafish.R;
import com.example.anaghafish.user.userui.CartFragment;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    private List<Product> productList;
    private RecyclerView.Adapter adapter;
    private CartFragment cartFragment;

    public ProductFragment(CartFragment cartFragment) {
        this.cartFragment = cartFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.productRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize productList and add your products here (replace with your actual product data)
        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.raw, "Product 1", 10.99, 0));
        productList.add(new Product(R.drawable.fish2, "Product 2", 12.99, 0));
        // Add more products as needed

        // Create and set the adapter for the RecyclerView
//        adapter = new ProductAdapter(productList);
//        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
        private List<Product> productList;

        public ProductAdapter(List<Product> productList) {
            this.productList = productList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Product product = productList.get(position);

            // Bind product data to the ViewHolder
            holder.productImage.setImageResource(product.getImageResource());
            holder.productName.setText(product.getProductName());
            holder.productPrice.setText(String.format("$%.2f", product.getPrice()));

            // Set an "Add to Cart" button click listener
            holder.addToCartButton.setOnClickListener(v -> {
                // Add the selected product to the cart (Call the addToCart method of your CartFragment)
                if (cartFragment != null) {
//                    cartFragment.addToCart(product);
                }
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }
    }

    private static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        Button addToCartButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
