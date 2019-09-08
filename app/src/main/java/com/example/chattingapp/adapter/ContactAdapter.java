package com.example.chattingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chattingapp.MessageActivity;
import com.example.chattingapp.R;
import com.example.chattingapp.model.User;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;
    private boolean isChat;

    public ContactAdapter(List<User> userList, Context context, boolean isChat) {
        this.userList = userList;
        this.context = context;
        this.isChat = isChat;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.nameTV.setText(user.getName());

        if(user.getProfileImage().equals("default")){
            holder.profileImage.setImageResource(R.drawable.ic_launcher_background);
        }else {
            Glide.with(context).load(user.getProfileImage()).into(holder.profileImage);
        }

        if(isChat){
            if(user.getStatus().equals("online")){

                holder.status_off.setVisibility(View.GONE);
                holder.status_on.setVisibility(View.VISIBLE);

            }else{

                holder.status_off.setVisibility(View.VISIBLE);
                holder.status_on.setVisibility(View.GONE);


            }

        }else {
            holder.status_off.setVisibility(View.GONE);
            holder.status_on.setVisibility(View.GONE);

        }

        //Glide.with(context).load(user.getProfileImage()).into(holder.profileImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context, ""+user.getId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("name",user.getId());
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage,status_on, status_off;
        private TextView nameTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            nameTV = itemView.findViewById(R.id.nameTV);
            status_on = itemView.findViewById(R.id.statusOn);
            status_off = itemView.findViewById(R.id.statusOff);
        }
    }
}
