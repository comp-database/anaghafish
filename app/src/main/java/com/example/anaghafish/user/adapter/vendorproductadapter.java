package com.example.anaghafish.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anaghafish.R;
import com.example.anaghafish.user.model.VendorProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class vendorproductadapter extends RecyclerView.Adapter<vendorproductadapter.ViewHolder> {

    private ArrayList<VendorProduct> dataList;
    private Context context;

    public vendorproductadapter(ArrayList<VendorProduct> dataList) {

        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VendorProduct item = dataList.get(position);

        Picasso.get().load(item.getImageUrl()).into(holder.imageView);
        holder.productNameTextView.setText(item.getProductName());
        holder.vendorNameTextView.setText(item.getVendorName());
        holder.priceTextView.setText(item.getPrice());


    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productNameTextView;
        TextView vendorNameTextView;
        TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fishImageView1);
            productNameTextView = itemView.findViewById(R.id.fishNameTextView1);
            vendorNameTextView = itemView.findViewById(R.id.vendorNameTextView1);
            priceTextView = itemView.findViewById(R.id.fishPriceTextView1);
        }
    }
}
