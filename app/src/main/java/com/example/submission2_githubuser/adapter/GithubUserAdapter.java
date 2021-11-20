package com.example.submission2_githubuser.adapter;

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
import com.example.submission2_githubuser.R;
import com.example.submission2_githubuser.form.DetailUserActivity;
import com.example.submission2_githubuser.model.ItemsItem;


import java.util.List;

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserAdapter.ListViewHolder> {
    protected List<ItemsItem> listUser;
    Context context;

    public GithubUserAdapter(List<ItemsItem> listUser, Context context) {
        this.listUser = listUser;
        this.context = context;
    }


    @NonNull
    @Override
    public GithubUserAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_user, viewGroup, false);
       return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubUserAdapter.ListViewHolder holder, int position) {
        ItemsItem githubUser = listUser.get(position);
        Glide.with(holder.itemView.getContext())
                .load(githubUser.getAvatarUrl())
                .circleCrop()
                .into(holder.imgAvatar);
        holder.username.setText(githubUser.getLogin());

    }

    @Override
    public int getItemCount() {
        return listUser.size();

    }

    public void setListUser(List<ItemsItem> listUser) {
        this.listUser = listUser;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgAvatar;
        TextView username;
        public ListViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_item_avatar);
            username = itemView.findViewById(R.id.tv_item_username);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
          if(position != RecyclerView.NO_POSITION) {
              ItemsItem detailUser = listUser.get(position);
              Intent intent = new Intent(context, DetailUserActivity.class);
              intent.putExtra("username", listUser.get(position).getLogin());

              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              context.startActivity(intent);
              Toast.makeText(v.getContext(), detailUser.getLogin(), Toast.LENGTH_SHORT).show();
          }
        }
    }
}
