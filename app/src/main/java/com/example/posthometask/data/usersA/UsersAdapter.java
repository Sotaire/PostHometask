package com.example.posthometask.data.usersA;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posthometask.R;
import com.example.posthometask.data.models.PostModel;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<PostModel> postModels = new ArrayList<>();
    int userId = 0;
    OnUserClickListener onUserClickListener;

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    public void setPostModels(ArrayList<PostModel> postModels, int userId) {
        this.postModels = postModels;
        this.userId = userId;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1){
            return new UsersAdapter.UsersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view_holder,parent,false));
        }else {
            Log.d("log","tes");
            return new ListViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.post_view_holder,parent,false));
        }
    }

    //    @NonNull
//    @Override
//    public UsersAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new UsersAdapter.UsersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view_holder,parent,false));
//    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (userId == 0){
            if (postModels.get(position).getUser()!=null){
                ((UsersViewHolder)holder).onBind(postModels.get(position).getUser().toString());
        }
        }else{
            if (postModels.get(position).getTitle() != null){
                ((ListViewHolder)holder).title.setText(postModels.get(position).getTitle());
            }
            if (postModels.get(position).getContent() != null) {
                ((ListViewHolder)holder).content.setText(postModels.get(position).getContent());
            }
            if (postModels.get(position).getUser() != null){
                ((ListViewHolder)holder).user.setText(postModels.get(position).getUser().toString());
            }
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
//        if (postModels.get(position).getUser()!=null){
//            holder.onBind(postModels.get(position).getUser().toString());
//        }
//    }

    @Override
    public int getItemViewType(int position) {
        if (userId == 0){
            return 1;
        }else{
            return 2;
        }
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUserClickListener.onCLick(getAdapterPosition());
                }
            });
        }

        public void onBind(String id){
            userId.setText(id);
        }
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView title,content,user;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_post_tv);
            content = itemView.findViewById(R.id.content_post_tv);
            user = itemView.findViewById(R.id.user_post_tv);
        }

        public void bind(String title,String content,String user){
            this.title.setText(title);
            this.content.setText(content);
            this.user.setText(user);
        }
    }

}
