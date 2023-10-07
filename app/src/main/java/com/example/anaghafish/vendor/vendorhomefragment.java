package com.example.anaghafish.vendor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.anaghafish.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class vendorhomefragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView selectedImageView;
    private EditText vendorNameEditText;
    private EditText productNameEditText;
    private EditText priceEditText;
    private EditText phoneNumberEditText; // Add EditText for phone number

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendorhomefragment, container, false);

        // Initialize UI elements
        selectedImageView = view.findViewById(R.id.selected_image_view);
        vendorNameEditText = view.findViewById(R.id.vendor_name_edittext);
        productNameEditText = view.findViewById(R.id.product_name_edittext);
        priceEditText = view.findViewById(R.id.price_edittext);
        phoneNumberEditText = view.findViewById(R.id.phone_edittext); // Initialize phone number EditText
        Button selectImageButton = view.findViewById(R.id.select_image_button);
        Button uploadButton = view.findViewById(R.id.upload_button);

        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("VendorProductlist"); // Replace with your database node

        // Set a click listener for the "Select Image" button
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start an image picker intent
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        // Set a click listener for the "Upload" button
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String productName = productNameEditText.getText().toString();
                String vendorName = vendorNameEditText.getText().toString();
                String price = priceEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString(); // Get phone number input

                // Check if any of the fields is empty
                if (productName.isEmpty() || vendorName.isEmpty() || price.isEmpty() || phoneNumber.isEmpty()) {
                    // Display an error message indicating that all fields must be filled
                    Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
                } else {
                    // All fields are filled, proceed to upload the image
                    uploadImageToStorage(productName, vendorName, price, phoneNumber);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data != null) {
            // Retrieve the selected image and display it in the ImageView
            try {
                // Decode the image with options to reduce its size
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4; // Adjust this value as needed to control image size
                Bitmap selectedImage = BitmapFactory.decodeStream(
                        requireContext().getContentResolver().openInputStream(data.getData()),
                        null,
                        options
                );

                if (selectedImage != null) {
                    selectedImageView.setImageBitmap(selectedImage);
                    selectedImageView.setVisibility(View.VISIBLE);
                } else {
                    // Handle case where the selected image couldn't be decoded
                    // Log an error or display a message to the user
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Handle the exception, e.g., log it or display an error message
            }
        }
    }

    private void uploadImageToStorage(final String productName, final String vendorName, final String price, final String phoneNumber) {
        // Get the selected image as a Bitmap
        Bitmap selectedImage = ((BitmapDrawable) selectedImageView.getDrawable()).getBitmap();

        // Create a unique filename for the image (e.g., using a timestamp)
        String timestamp = String.valueOf(System.currentTimeMillis());
        String imageName = "image_" + timestamp + ".jpg";

        // Create a reference to the image in Firebase Storage
        StorageReference imageRef = storageRef.child(imageName);

        // Compress and upload the image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos); // Adjust compression quality as needed
        byte[] imageData = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful
                // Get the download URL of the uploaded image
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();

                        // Save the data (image URL, product name, vendor name, price, email, phone number) to Firebase Realtime Database
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference userRef = databaseRef.child(userId); // Use the user's ID as a node
                        String itemId = userRef.push().getKey(); // Generate a unique key for the item

                        Map<String, Object> itemData = new HashMap<>();
                        itemData.put("image_url", imageUrl);
                        itemData.put("product_name", productName);
                        itemData.put("vendor_name", vendorName);
                        itemData.put("price", price);
                        itemData.put("phone_number", phoneNumber); // Include phone number

                        userRef.child(itemId).setValue(itemData);

                        // Display a success message or navigate to another screen
                        Toast.makeText(requireContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle image upload failure
                Toast.makeText(requireContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("YourTag", "Image upload failed: " + e.getMessage(), e);
            }
        });
    }
}
