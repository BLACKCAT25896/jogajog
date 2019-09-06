package com.example.chattingapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chattingapp.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;


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

         binding.logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseAuth.getInstance().signOut();
                 startActivity(new Intent(getContext(),SignInActivity.class));
             }
         });

         return view;
    }

}
