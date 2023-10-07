package com.example.anaghafish.user.userui;

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

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser; // FirebaseUser to get the current user
    private EditText userName;
    private EditText userPhone;
    private EditText userEmail;
    private Button editProfileButton;
    private Button saveButton;
    private Button logoutButton;
    private SharedPreferences sharedPreferences;

    private boolean editMode = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        userName = view.findViewById(R.id.user_name);
        userPhone = view.findViewById(R.id.user_phone);
        userEmail = view.findViewById(R.id.user_email);
        editProfileButton = view.findViewById(R.id.edit_profile_button);
        saveButton = view.findViewById(R.id.save_button);
        logoutButton = view.findViewById(R.id.logout);

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Get the current user
        currentUser = mAuth.getCurrentUser();

        // Load saved data
        loadUserData();

        // Set click listeners
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode = !editMode;
                updateUI(editMode);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
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

        saveButton.setOnClickListener(new View.OnClickListener() {
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
            userName.setEnabled(true);
            userPhone.setEnabled(true);
            userEmail.setEnabled(true);
            saveButton.setVisibility(View.VISIBLE);
        } else {
            userName.setEnabled(false);
            userPhone.setEnabled(false);
            userEmail.setEnabled(false);
            saveButton.setVisibility(View.GONE);
        }
    }

    private void loadUserData() {
        // Use the current user's email as part of the key
        String userEmailKey = currentUser != null ? currentUser.getEmail() : "";
        String defaultString = "";

        // Load saved data from SharedPreferences with a unique key for each user
        userName.setText(sharedPreferences.getString("userName_" + userEmailKey, defaultString));
        userPhone.setText(sharedPreferences.getString("userPhone_" + userEmailKey, defaultString));
        userEmail.setText(sharedPreferences.getString("userEmail_" + userEmailKey, defaultString));
    }

    private void saveUserData() {
        // Use the current user's email as part of the key
        String userEmailKey = currentUser != null ? currentUser.getEmail() : "";

        // Save user data to SharedPreferences with a unique key for each user
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName_" + userEmailKey, userName.getText().toString());
        editor.putString("userPhone_" + userEmailKey, userPhone.getText().toString());
        editor.putString("userEmail_" + userEmailKey, userEmail.getText().toString());
        editor.apply();
    }
}
