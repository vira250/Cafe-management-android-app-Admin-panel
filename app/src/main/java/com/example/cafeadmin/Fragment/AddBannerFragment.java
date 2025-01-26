package com.example.cafeadmin.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cafeadmin.MainActivity;
import com.example.cafeadmin.Model.ImageSliderModel;
import com.example.cafeadmin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class AddBannerFragment extends Fragment {

    String id;
    private Uri uri;

    ImageView addImage;
    Button selectImage;
    Button addBannerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_banner, container, false);


        // to pick the images from the gallery
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



        addBannerBtn = root.findViewById(R.id.addBannersBtn);
        addBannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addImageSlider();
                uploadImage();

            }
        });

        return root;
    }


    // method for add the banner image  in storage
    private void uploadImage() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Image Slider/" + id);


        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        FirebaseFirestore.getInstance()
                                                .collection("Image Slider")
                                                .document(id)
                                                .update("image",uri.toString());

                                        Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();

                                        addImage.setImageDrawable(null);



                                    }
                                });
                    }
                });


    }





    //method for add the banner in database
    private void addImageSlider() {

        id = UUID.randomUUID().toString();
        ImageSliderModel imageSliderModel = new ImageSliderModel(id,null,true);
        FirebaseFirestore.getInstance()
                .collection("Image Slider")
                .document(id)
                .set(imageSliderModel);

        Toast.makeText(getActivity(), "Banners Added", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarVisibility(false);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){

            uri = data.getData();
            addImage.setImageURI(uri);


   }

}

}
