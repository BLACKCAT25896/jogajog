package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.chattingapp.databinding.ActivitySignUpBinding;
import com.example.chattingapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String name, email, password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);



        init();
        binding.signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });


        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputData();

            }
        });


    }

    private void inputData() {
        name = binding.signUpNameET.getText().toString();
        email = binding.signUpEmailET.getText().toString();
        password = binding.signUpPasswordET.getText().toString();


        name = binding.signUpNameET.getText().toString().trim();
        if (name.isEmpty()) {
            binding.signUpNameET.setError("Please input your name !");
        }
        email = binding.signUpEmailET.getText().toString().trim();

        if (email.isEmpty()) {
            binding.signUpEmailET.setError("please input email !");

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            binding.signUpEmailET.setError("please enter valid email address !");

        }
        password = binding.signUpPasswordET.getText().toString().trim();
        if (password.isEmpty()) {
            binding.signUpPasswordET.setError("Please input password !");

        } else if (password.length() < 6) {
            binding.signUpPasswordET.setError("password 6 digit or more");


        } else {

            register(name, email, password);
        }
    }

    private void register(final String name, final String email, final String password) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    final User user = new User(userId, name, email, "default", "offline", name.toLowerCase());
                    DatabaseReference userRef = databaseReference.child("users").child(userId);
                    userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SignUpActivity.this, "SuccessFully SignUp", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();



                        }

                    });


                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        finish();


    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }
}