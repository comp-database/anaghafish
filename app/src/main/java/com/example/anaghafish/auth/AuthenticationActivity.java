package com.example.anaghafish.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.anaghafish.R;
import com.google.firebase.FirebaseApp;

public class AuthenticationActivity extends AppCompatActivity {


    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        FirebaseApp.initializeApp(this);
        // Find the NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.auth_nav_host_fragment);

        // Get the NavController from the NavHostFragment
        navController = navHostFragment.getNavController();
    }

    // Optionally, you can override the onBackPressed method to handle back navigation.
    @Override
    public void onBackPressed() {
        if (navController.popBackStack()) {
            // Navigation handled by the NavController
        } else {
            // No more fragments in the back stack, so let the default back behavior occur.
            super.onBackPressed();
        }
    }
}
