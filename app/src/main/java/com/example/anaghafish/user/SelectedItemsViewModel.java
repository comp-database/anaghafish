package com.example.anaghafish.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.anaghafish.user.model.Product;

import java.util.List;

public class SelectedItemsViewModel extends ViewModel {
    private MutableLiveData<List<Product>> selectedItems = new MutableLiveData<>();

    public MutableLiveData<List<Product>> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Product> items) {
        selectedItems.setValue(items);
    }
}
