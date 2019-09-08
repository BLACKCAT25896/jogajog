package com.example.chattingapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chattingapp.adapter.ContactAdapter;
import com.example.chattingapp.databinding.FragmentContactBinding;
import com.example.chattingapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private FragmentContactBinding binding;
    private ContactAdapter adapter;
    private List<User>userList;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    static ContactFragment instance;

    public static ContactFragment getInstance() {
        if(instance == null)
            instance = new ContactFragment();

        return instance;

    }


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate( inflater,R.layout.fragment_contact, container, false);
        binding.contactRecyclerView.setHasFixedSize(true);
        binding.contactRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        View view= binding.getRoot();

        init();
        getUserInfo();
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchContact(charSequence.toString().toLowerCase());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void searchContact(String s) {

       // final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("search")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    userList.clear();

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        User user = data.getValue(User.class);
                        assert user != null;
                        assert userList != null;
                        if (!user.getId().equals(firebaseUser.getUid())) {
                            userList.add(user);
                        }
                    }
                    binding.contactRecyclerView.setAdapter(adapter);

                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getUserInfo() {
        DatabaseReference useRef = databaseReference.child("users");
        useRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (binding.search.getText().toString().equals("")) {
                    userList.clear();
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        User user = data.getValue(User.class);

                        assert user != null;
                        assert userList != null;
                        if (!user.getId().equals(firebaseUser.getUid())) {
                            userList.add(user);
                        }
                        //userList.add(user);
                        adapter.notifyDataSetChanged();

                    }
                    binding.contactRecyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void init() {
        userList = new ArrayList<>();
        adapter = new ContactAdapter(userList,getContext(),false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

}
