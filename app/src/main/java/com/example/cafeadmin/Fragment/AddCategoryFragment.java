package com.example.cafeadmin.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafeadmin.MainActivity;
import com.example.cafeadmin.Model.CategoryModel;
import com.example.cafeadmin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class AddCategoryFragment extends Fragment {

    private EditText categoryEdt;

    private String id, category;
    private Uri uri;

    ImageView addImage;
    Button addCategoryBtn;

    Button selectImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_category, container, false);


        categoryEdt = root.findViewById(R.id.category);

        addCategoryBtn = root.findViewById(R.id.addCategoryBtn);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                category = categoryEdt.getText().toString().trim();
                TextView errorMessage = root.findViewById(R.id.errorMessage);



                categoryEdt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // not needed
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // hide the error message if the field is not empty
                        if (!TextUtils.isEmpty(charSequence)) {
                            errorMessage.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });


                if (TextUtils.isEmpty(category)) {

                    errorMessage.setVisibility(View.VISIBLE);
                    errorMessage.setText("Please Enter The Category Name.");

                }else {

                    addCategory();
                    uploadImage();

                }

            }
        });


        // for pick the image from gallery
        addImage = root.findViewById(R.id.addImage);
        selectImage = root.findViewById(R.id.selectImageBtn);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);

            }
        });


        return root;

    }

    private void uploadImage() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Category/"+id+".png");

        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        FirebaseFirestore.getInstance()
                                                .collection("Category")
                                                .document(id)
                                                .update("image",uri.toString());

                                        Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                                        categoryEdt.getText().clear();
                                        addImage.setImageDrawable(null);




                                    }
                                });
                    }
                });
    }



    private void addCategory() {


        id = UUID.randomUUID().toString();
        CategoryModel categoryModel = new CategoryModel(id,category,null,true);

// Check if the category already exists
        FirebaseFirestore.getInstance()
                .collection("Category")
                .whereEqualTo("category", category)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        // Category already exists, show error message
                        Toast.makeText(getActivity(), "Category already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        // Category does not exist, add it to the database
                        FirebaseFirestore.getInstance()
                                .collection("Category")
                                .document(id)
                                .set(categoryModel);

                        Toast.makeText(getActivity(), "Category Added", Toast.LENGTH_SHORT).show();


                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){

            uri = data.getData();
            addImage.setImageURI(uri);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarVisibility(false);

    }

}