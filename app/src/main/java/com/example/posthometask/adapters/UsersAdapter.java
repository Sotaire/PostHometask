package com.example.posthometask.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posthometask.R;
import com.example.posthometask.data.models.PostModel;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    ArrayList<PostModel> postModels = new ArrayList<>();

    public void setPostModels(ArrayList<PostModel> postModels) {
        this.postModels = postModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.onBind(postModels.get(position).getUser().toString());
    }

    @Override
    public int getItemCount() {
        return postModels.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView userId;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.user);
        }

        public void onBind(String id){
            userId.setText(id);
        }
    }

}
