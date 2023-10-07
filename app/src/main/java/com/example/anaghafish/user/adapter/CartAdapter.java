package com.example.anaghafish.user.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.anaghafish.R;
import com.example.anaghafish.user.model.Product;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Product> cartItemList;

    public CartAdapter(List<Product> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItemList.get(position);
        holder.productNameTextView.setText(product.getProductName());
        holder.productPriceTextView.setText(product.getPrice());
        Picasso.get().load(product.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cartItemList != null ? cartItemList.size() : 0;
    }

    public void setItems(List<Product> cartItemList) {
        this.cartItemList = cartItemList;
        notifyDataSetChanged();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView productPriceTextView;
        public ImageView imageView;

        public CartViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cartProductImage);
            productNameTextView = itemView.findViewById(R.id.cartProductName);
            productPriceTextView = itemView.findViewById(R.id.cartProductPrice);
        }
    }
}
