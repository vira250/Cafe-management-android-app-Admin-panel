package com.example.cafeadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafeadmin.Model.AdminModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {


    Button signup;
    EditText name, email, password, address, mobile;
    TextView signIn;
    FirebaseAuth auth;
    ProgressBar progressBar;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        signup=findViewById(R.id.button);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signIn=findViewById(R.id.redirectTxtSignIn);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.mobile);





        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();
                progressBar.setVisibility(View.VISIBLE);

            }
        });


        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        }

    }

    private void createUser() {

        String adminName=name.getText().toString();
        String adminEmail=email.getText().toString();
        String adminAddress = address.getText().toString();
        String adminMobile = mobile.getText().toString();
        String adminPassword=password.getText().toString();




        Pattern pattern = Pattern.compile("^[6789]\\d{9}$");
        Matcher matcher = pattern.matcher(adminMobile);

        if (matcher.matches()){

        }else {
            Toast.makeText(this, "Please Enter a valid Phone Number", Toast.LENGTH_SHORT).show();
        }



        if (TextUtils.isEmpty(adminName)){
            Toast.makeText(this, "Name is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(adminEmail)){
            Toast.makeText(this, "Email is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(adminPassword)){
            Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (adminPassword.length()<6){
            Toast.makeText(this, "Password Length must be greater than 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(adminAddress)) {
            Toast.makeText(this, "Address is Empty ! ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(adminMobile)) {
            Toast.makeText(this, "Mobile is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(adminEmail, adminPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String id = auth.getUid();
                            AdminModel adminModel=new AdminModel(adminName, adminEmail, adminPassword, adminAddress,adminMobile,id);
                            database.getReference().child("Admin").child(id).setValue(adminModel);
                            progressBar.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            finish();

                        }
                        else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this, "User Account Already Exist with this Email", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
}