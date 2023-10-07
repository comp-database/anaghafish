package com.example.anaghafish.user.userui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.anaghafish.R;
import com.example.anaghafish.user.SelectedItemsViewModel;
import com.example.anaghafish.user.adapter.CartAdapter;
import com.example.anaghafish.user.model.Product;
import java.util.List;

public class CartFragment extends Fragment {
    private CartAdapter cartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        // Initialize SelectedItemsViewModel
        SelectedItemsViewModel selectedItemsViewModel = new ViewModelProvider(requireActivity()).get(SelectedItemsViewModel.class);

        // Initialize RecyclerView
        RecyclerView cartRecyclerView = rootView.findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create and set the adapter
        cartAdapter = new CartAdapter(selectedItemsViewModel.getSelectedItems().getValue());
        cartRecyclerView.setAdapter(cartAdapter);

        // Observe changes in selected items and update the adapter
        selectedItemsViewModel.getSelectedItems().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> selectedItems) {
                // Update the CartAdapter when the selected items change
                cartAdapter.setItems(selectedItems);
            }
        });

        return rootView;
    }
}
