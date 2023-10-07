package com.example.anaghafish.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.anaghafish.R;
import com.example.anaghafish.user.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpUserFragment extends Fragment {

    // Initialize Firebase Authentication
    private FirebaseAuth mAuth;

    // Initialize the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Get a reference to a specific node in the database
    DatabaseReference myRef = database.getReference("Authentication/userList");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_user, container, false);

        mAuth = FirebaseAuth.getInstance();

        EditText emailET = view.findViewById(R.id.sign_up_email);
        EditText passwordET = view.findViewById(R.id.sign_up_password);

        // Handle sign-up button click
        view.findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                // Implement Firebase sign-up here
                // Example:
                signUpWithEmailAndPassword(email, password);
            }
        });

        // Handle navigation back to sign-in fragment
        view.findViewById(R.id.back_to_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });

        return view;
    }

    private void signUpWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign-up success, navigate to the next fragment or activity
                            // Example: Navigation.findNavController(view).navigate(R.id.nextFragment);
                            myRef.push().setValue(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
                            Intent intent = new Intent(getActivity(), UserActivity.class);
                            startActivity(intent);
                        } else {
                            // Sign-up failed, handle the error
                            Toast.makeText(requireContext(), "Registration failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
