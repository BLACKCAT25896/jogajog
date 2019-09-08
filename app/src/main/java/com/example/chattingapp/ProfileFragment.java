package com.example.chattingapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chattingapp.databinding.FragmentProfileBinding;
import com.example.chattingapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String proName;


    static ProfileFragment instance;

    public static ProfileFragment getInstance() {
        if(instance == null)
            instance = new ProfileFragment();

        return instance;

    }


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false);
         View view = binding.getRoot();

         init();

         binding.logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseAuth.getInstance().signOut();
                 startActivity(new Intent(getContext(),SignInActivity.class));
             }
         });

        getAccountName();

         return view;
    }

    private void init() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }

    private void getAccountName() {
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = databaseReference.child("users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                proName = dataSnapshot.child("name").getValue().toString();
                binding.yourProfile.setText(proName);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

}
