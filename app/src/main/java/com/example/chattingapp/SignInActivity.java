package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.chattingapp.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private FirebaseAuth firebaseAuth;
    private String email, password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);

        init();

        String emaill = getIntent().getStringExtra("email");
        String pass = getIntent().getStringExtra("pass");

        binding.signInEmailET.setText(emaill);
        binding.signInPasswordET.setText(pass);

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }


        binding.signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
            }
        });


        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = binding.signInEmailET.getText().toString();
                password = binding.signInPasswordET.getText().toString();
                email = binding.signInEmailET.getText().toString().trim();

                if (email.isEmpty()) {
                    binding.signInEmailET.setError("please input email !");

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    binding.signInEmailET.setError("please enter valid email address !");

                }
                password = binding.signInPasswordET.getText().toString().trim();
                if (password.isEmpty()) {
                    binding.signInPasswordET.setError("Please input password !");

                } else if (password.length() < 6) {
                    binding.signInPasswordET.setError("password 6 digit or more");


                } else {

                    login(email, password);

                }


            }
        });


    }

    private void login(String email, String password) {
        progressDialog.setTitle("wait....!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    progressDialog.dismiss();
                    finish();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignInActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

    }


}