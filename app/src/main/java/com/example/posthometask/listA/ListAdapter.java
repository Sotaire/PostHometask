package com.example.posthometask.listA;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.posthometask.R;
import com.example.posthometask.data.models.PostModel;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    ArrayList<PostModel> postBodies = new ArrayList<>();
    PostClickListener listener;

    public void setListener(PostClickListener listener) {
        this.listener = listener;
    }

    public void setPostBodies(ArrayList<PostModel> list){
        postBodies = list;
        notifyDataSetChanged();
    }

    public void deletePost(int position){
        postBodies.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.post_view_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        if (postBodies.get(position).getTitle() != null){
            holder.title.setText(postBodies.get(position).getTitle());
        }
        if (postBodies.get(position).getContent() != null) {
            holder.content.setText(postBodies.get(position).getContent());
        }
        if (postBodies.get(position).getUser() != null){
            holder.user.setText(postBodies.get(position).getUser().toString());
        }
    }

    @Override
    public int getItemCount() {
        return postBodies.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView title,content,user;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_post_tv);
            content = itemView.findViewById(R.id.content_post_tv);
            user = itemView.findViewById(R.id.user_post_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongCLick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(String title,String content,String user){
            this.title.setText(title);
            this.content.setText(content);
            this.user.setText(user);
        }

    }

}
