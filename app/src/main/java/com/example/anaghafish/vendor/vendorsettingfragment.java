package com.example.anaghafish.vendor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.anaghafish.R;
import com.example.anaghafish.auth.AuthenticationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class vendorsettingfragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser; // FirebaseUser to get the current user
    private EditText userName1;
    private EditText userPhone1;
    private EditText userEmail1;
    private Button editProfileButton1;
    private Button saveButton1;
    private Button logoutButton1;
    private SharedPreferences sharedPreferences;

    private boolean editMode = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendorsettingfragment, container, false);

        // Initialize UI elements
        userName1 = view.findViewById(R.id.user_name);
        userPhone1 = view.findViewById(R.id.user_phone);
        userEmail1 = view.findViewById(R.id.user_email);
        editProfileButton1 = view.findViewById(R.id.edit_profile_button);
        logoutButton1 = view.findViewById(R.id.logout);
        saveButton1 = view.findViewById(R.id.save_button);

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Get the current user
        currentUser = mAuth.getCurrentUser();

        // Load saved data
        loadUserData();

        // Set click listeners
        editProfileButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode = !editMode;
                updateUI(editMode);
            }
        });

        logoutButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the user
                mAuth.signOut();

                // Redirect to the authentication activity
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        saveButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
                editMode = false;
                updateUI(editMode);
            }
        });

        // Initial UI setup
        updateUI(editMode);

        return view;
    }

    private void updateUI(boolean editMode) {
        if (editMode) {
            userName1.setEnabled(true);
            userPhone1.setEnabled(true);
            userEmail1.setEnabled(true);
            saveButton1.setVisibility(View.VISIBLE);
        } else {
            userName1.setEnabled(false);
            userPhone1.setEnabled(false);
            userEmail1.setEnabled(false);
            saveButton1.setVisibility(View.GONE);
        }
    }

    private void loadUserData() {
        // Use the current user's email as part of the key
        String userEmailKey = currentUser != null ? currentUser.getEmail() : "";
        String defaultString = "";

        // Load saved data from SharedPreferences with a unique key for each user
        userName1.setText(sharedPreferences.getString("userName_" + userEmailKey, defaultString));
        userPhone1.setText(sharedPreferences.getString("userPhone_" + userEmailKey, defaultString));
        userEmail1.setText(sharedPreferences.getString("userEmail_" + userEmailKey, defaultString));
    }

    private void saveUserData() {
        // Use the current user's email as part of the key
        String userEmailKey = currentUser != null ? currentUser.getEmail() : "";

        // Save user data to SharedPreferences with a unique key for each user
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName_" + userEmailKey, userName1.getText().toString());
        editor.putString("userPhone_" + userEmailKey, userPhone1.getText().toString());
        editor.putString("userEmail_" + userEmailKey, userEmail1.getText().toString());
        editor.apply();
    }
}
