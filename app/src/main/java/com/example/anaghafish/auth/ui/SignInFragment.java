package com.example.anaghafish.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.anaghafish.R;
import com.example.anaghafish.user.UserActivity;
import com.example.anaghafish.vendor.VenderActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SignInFragment extends Fragment {

    // Initialize Firebase Authentication
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Get a reference to a specific node in the database
    DatabaseReference myRef = database.getReference("Authentication");

    DatabaseReference userListRef = myRef.child("userList");
    DatabaseReference vendorListRef = myRef.child("venderList");

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (mAuth.getCurrentUser() != null){
//            Query userQuery = userListRef.orderByValue().equalTo(mAuth.getCurrentUser().getEmail());
//            Query vendorQuery = vendorListRef.orderByValue().equalTo(mAuth.getCurrentUser().getEmail());
//
//            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot userSnapshot) {
//                    if (userSnapshot.exists()) {
//                        // The email exists in the "userList"
//                        // User is a regular user
//                        // Redirect to the user's screen
//                        Intent intent = new Intent(getActivity(), UserActivity.class);
//                        startActivity(intent);
//                    } else {
//                        // The email doesn't exist in the "userList"
//                        // Check the "vendorList"
//                        vendorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot vendorSnapshot) {
//                                if (vendorSnapshot.exists()) {
//                                    // The email exists in the "vendorList"
//                                    // User is a vendor
//                                    // Redirect to the vendor's screen
//                                    Intent intent = new Intent(getActivity(), VenderActivity.class);
//                                    startActivity(intent);
//                                } else {
//                                    // The email doesn't exist in the "vendorList" either
//                                    // Handle the case where the user is neither a user nor a vendor
//                                    Toast.makeText(getActivity(),"Wrong Email",Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                // Handle errors
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Handle errors
//                }
//            });
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        mAuth = FirebaseAuth.getInstance();
        EditText emailET = view.findViewById(R.id.sign_in_email);
        EditText passwordET = view.findViewById(R.id.sign_in_password);

        // Handle sign-in button click
        view.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement Firebase sign-in here
                // Example:
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();
                signInWithEmailAndPassword(email, password);
            }
        });

        // Handle navigation to sign-up fragment
        view.findViewById(R.id.sign_up_button_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });
        view.findViewById(R.id.sign_up_button_vender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_signUpVenderFragment);
            }
        });

        return view;
    }

    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign-in success, navigate to the next fragment or activity
                            // Example: Navigation.findNavController(view).navigate(R.id.nextFragment);

                            // Get references to the "userList" and "vendorList" nodes
                            // Create a ValueEventListener to check the "userList"
                            Query userQuery = userListRef.orderByValue().equalTo(email);
                            Query vendorQuery = vendorListRef.orderByValue().equalTo(email);

                            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot userSnapshot) {
                                    if (userSnapshot.exists()) {
                                        // The email exists in the "userList"
                                        // User is a regular user
                                        // Redirect to the user's screen
                                        Intent intent = new Intent(getActivity(), UserActivity.class);
                                        startActivity(intent);
                                        requireActivity().finish();
                                    } else {
                                        // The email doesn't exist in the "userList"
                                        // Check the "vendorList"
                                        vendorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot vendorSnapshot) {
                                                if (vendorSnapshot.exists()) {
                                                    // The email exists in the "vendorList"
                                                    // User is a vendor
                                                    // Redirect to the vendor's screen
                                                    Intent intent = new Intent(getActivity(), VenderActivity.class);
                                                    startActivity(intent);
                                                    requireActivity().finish();
                                                } else {
                                                    // The email doesn't exist in the "vendorList" either
                                                    // Handle the case where the user is neither a user nor a vendor
                                                    Toast.makeText(getActivity(),"Wrong Email ",Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                // Handle errors
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Handle errors
                                }
                            });
//                            Intent intent = new Intent(getActivity(), UserActivity.class);
//                            startActivity(intent);
                        } else {
                            // Sign-in failed, handle the error
                            Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
