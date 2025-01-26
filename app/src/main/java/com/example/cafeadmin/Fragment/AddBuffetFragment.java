package com.example.cafeadmin.Fragment;

import static android.app.Activity.RESULT_OK;
import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafeadmin.Model.BuffetModel;
import com.example.cafeadmin.Model.CategoryModel;
import com.example.cafeadmin.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class AddBuffetFragment extends Fragment {


    Spinner categorySpinner;

    EditText discriptionedt, buffetPrice_edt , buffetName_edt;

    ImageView addImage;

    Button selectImage, addBuffetbtn;

    String id, BuffetName, BuffetPrice, BuffetDiscription, category;

    private List<Uri> imageUris = new ArrayList<>();

    private ArrayList<String> categoryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_buffet, container, false);

        categorySpinner = root.findViewById(R.id.categorySpinner);
        discriptionedt = root.findViewById(R.id.description);
        buffetName_edt = root.findViewById(R.id.buffet);
        buffetPrice_edt = root.findViewById(R.id.buffet_price);
        addImage = root.findViewById(R.id.addImage);
        selectImage = root.findViewById(R.id.selectImageBtn);
        addBuffetbtn = root.findViewById(R.id.addBuffetbtn);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList.add(getString(R.string.spinner_hint));
        categorySpinner.setAdapter(categoryAdapter);

// Query Firebase to get categories
        FirebaseFirestore.getInstance()
                .collection("Category")
                .orderBy("category", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            categoryList.add(document.getString("category"));
                        }
                        categoryAdapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "Error getting categories: ", task.getException());
                    }
   });

        selectImage = root.findViewById(R.id.selectImageBtn);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // allow multiple image selection
                startActivityForResult(Intent.createChooser(intent, "Select Images"), 100);

            }
        });

        addBuffetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                category = categorySpinner.getSelectedItem().toString().trim();
                BuffetName= buffetName_edt.getText().toString().trim();
                BuffetDiscription = discriptionedt.getText().toString().trim();
                BuffetPrice = buffetPrice_edt.getText().toString().trim();

                addBuffet();
                uploadimage();
            }

        });
        return root;
    }

    public void uploadimage() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("All Products/" + id);

        for (int i = 0; i < imageUris.size(); i++) {
            Uri uri = imageUris.get(i);
            StorageReference imageReference = storageReference.child(i + ".png");
            UploadTask uploadTask = imageReference.putFile(uri);

            int finalI = i;
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                imageReference.getDownloadUrl().addOnSuccessListener(uri1 -> {
                    imageUris.set(finalI, Uri.parse(uri1.toString()));

                    // If all images have been uploaded, update the category document in Firestore
                    if (finalI == imageUris.size() - 1) {
                        FirebaseFirestore.getInstance()
                                .collection("All Products")
                                .document(id)

                                .update("imageUrls", imageUris)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getActivity(), "Images uploaded", Toast.LENGTH_SHORT).show();

                                    buffetName_edt.getText().clear();
                                    buffetPrice_edt.getText().clear();
                                    discriptionedt.getText().clear();

                                    categoryList.add(0, getString(R.string.spinner_hint));

                                    addImage.setImageURI(null);


                                });

                    }
                });
            });

        }
    }

    private void addBuffet() {

        if (category.equals(getString(R.string.spinner_hint))) {

            Toast.makeText(getActivity(), "Please select a category", Toast.LENGTH_SHORT).show();
        } else {

            id = UUID.randomUUID().toString();
            BuffetModel productModel = new BuffetModel(id, category, BuffetName,BuffetDiscription, BuffetPrice, null, true);
            FirebaseFirestore.getInstance()
                    .collection("All Products")
                    .document(id)
                    .set(productModel);

            Toast.makeText(getActivity(), "Buffet Added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                // multiple images selected
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    imageUris.add(uri);
                }
                // display the first selected image
                addImage.setImageURI(imageUris.get(0));
            } else {
                // single image selected
                Uri uri = data.getData();
                imageUris.add(uri);
                addImage.setImageURI(uri);

            }
        }
    }
}