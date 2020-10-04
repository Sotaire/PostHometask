package com.example.posthometask.ui.list;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posthometask.R;
import com.example.posthometask.listA.ListAdapter;
import com.example.posthometask.data.models.PostModel;
import com.example.posthometask.data.network.AndroidClient;
import com.example.posthometask.listA.PostClickListener;
import com.example.posthometask.ui.post.PostActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    public static final int POST = 1;
    public static final int UPDATE = 2;
    public static final String POST_KEY = "post1";
    public static final String UPDATE_KEY = "update2";
    ListAdapter listAdapter;
    FloatingActionButton floatingActionButton;
    ArrayList<PostModel> postModels = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.list_recycler_view);
        listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), PostActivity.class);
                intent.putExtra(POST_KEY,POST);
                startActivity(intent);
            }
        });
        refresh();
        getPosts();
        listAdapter.setListener(new PostClickListener() {
            @Override
            public void onLongCLick(int position) {
                Integer id = postModels.get(position).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("удалить запись " + postModels.get(position).getTitle() + "?");
                builder.setNegativeButton("отмена", null);
                builder.setPositiveButton("да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listAdapter.deletePost(position);
                        deleteData(id);
                    }
                });
                builder.show();
            }

            @Override
            public void onClick(int position) {
                Intent intent = new Intent(requireActivity(), PostActivity.class);
                intent.putExtra(POST_KEY,UPDATE);
                intent.putExtra(UPDATE_KEY,postModels.get(position).getId());
                intent.putExtra("title",postModels.get(position).getTitle());
                intent.putExtra("content",postModels.get(position).getContent());
                intent.putExtra("group",postModels.get(position).getGroup());
                intent.putExtra("id",postModels.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postModels.clear();
                getPosts();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void deleteData(int id){
        AndroidClient.getClient().deleteData(id).enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (response.isSuccessful()){
                    Log.d("delete", String.valueOf(response.body().getId()));
                }
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Log.d("delete",t.getMessage());
            }
        });
    }

    private void getPosts() {
        AndroidClient.getClient().getAllPost().enqueue(new Callback<ArrayList<PostModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                if (response.isSuccessful()){
                    if (postModels!= null){
                        postModels.clear();
                    }
                    postModels = response.body();
                listAdapter.setPostBodies(response.body());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                Log.d("Failure",t.getMessage());
            }
        });
    }

}