package com.example.anaghafish.user.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafish.R;
import com.example.anaghafish.user.model.FishProduct;

import java.util.List;

public class FishProductAdapter extends RecyclerView.Adapter<FishProductAdapter.ViewHolder> {
    private List<FishProduct> fishProducts;

    public FishProductAdapter(List<FishProduct> fishProducts) {
        this.fishProducts = fishProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FishProduct fishProduct = fishProducts.get(position);
        holder.fishImageView.setImageResource(fishProduct.getImageResourceId());
        holder.fishNameTextView.setText(fishProduct.getName());
        holder.fishPriceTextView.setText(String.format("%.2f", fishProduct.getPrice()) + "rs");
    }

    @Override
    public int getItemCount() {
        return fishProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fishImageView;
        TextView fishNameTextView;
        TextView fishPriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fishImageView = itemView.findViewById(R.id.fishImageView);
            fishNameTextView = itemView.findViewById(R.id.fishNameTextView);
            fishPriceTextView = itemView.findViewById(R.id.fishPriceTextView);
        }
    }
}

