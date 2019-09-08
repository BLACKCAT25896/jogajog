package com.example.chattingapp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chattingapp.adapter.ContactAdapter;
import com.example.chattingapp.databinding.FragmentChatBinding;
import com.example.chattingapp.model.Chat;
import com.example.chattingapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private FragmentChatBinding binding;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private ContactAdapter contactAdapter;
    private List<User> users;
    private List<String> userList;

    static ChatFragment instance;

    public static ChatFragment getInstance() {
        if (instance == null)
            instance = new ChatFragment();

        return instance;

    }


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        View view = binding.getRoot();
        init();
        getUserWchihYouChat();

        return view;
    }

    private void getUserWchihYouChat() {

        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats");
        DatabaseReference reference = databaseReference.child("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                userList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    Chat chat = data.getValue(Chat.class);

                    if (chat.getSender().equals(firebaseUser.getUid())) {

                        userList.add(chat.getReceiver());

                    }
                    if (chat.getReceiver().equals(firebaseUser.getUid())) {

                        userList.add(chat.getSender());
                    }
                }
                readChats();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void readChats() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    users.clear();

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        User user = data.getValue(User.class);

                        for (String id : userList) {
                            if (user.getId().equals(id)) {
                                if (users.size() != 0) {
                                    for (User me : users) {
                                        if (!user.getId().equals(me.getId())) {
                                            users.add(user);
                                        }

                                    }
                                } else {
                                    users.add(user);
                                }
                            }
                        }
                    }
                    binding.chatRecyclerView.setAdapter(contactAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void init() {
        users = new ArrayList<>();
        userList = new ArrayList<>();
        contactAdapter = new ContactAdapter(users, getContext(),true);

        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

}
