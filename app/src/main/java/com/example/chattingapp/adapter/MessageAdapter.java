package com.example.chattingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattingapp.MessageActivity;
import com.example.chattingapp.R;
import com.example.chattingapp.model.Chat;
import com.example.chattingapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private FirebaseUser firebaseUser;

    private List<Chat> chatList;
    private Context context;
    private String imageUrl;

    public MessageAdapter(List<Chat> chatList, Context context, String imageUrl) {
        this.chatList = chatList;
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_TYPE_RIGHT){
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right,parent,false);
            return new MessageAdapter.ViewHolder(view);

        }else {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.show_message.setText(chat.getMessage());

        if (position == chatList.size()-1){
            if(chat.isSeen()){
                holder.text_seen.setText("seen");
            }else {
                holder.text_seen.setText("Delivered");
            }
        }else {
            holder.text_seen.setVisibility(View.GONE);
        }





    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profile;
        public TextView show_message,text_seen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile);
            show_message = itemView.findViewById(R.id.showMessage);
            text_seen = itemView.findViewById(R.id.textSeen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chatList.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
