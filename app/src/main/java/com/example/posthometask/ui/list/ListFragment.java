package com.example.posthometask.ui.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.posthometask.R;
import com.example.posthometask.adapters.ListAdapter;
import com.example.posthometask.data.models.PostModel;
import com.example.posthometask.data.network.AndroidClient;
import com.example.posthometask.interfaces.PostClickListener;
import com.example.posthometask.ui.post.PostActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    ListAdapter listAdapter;
    FloatingActionButton floatingActionButton;
    ArrayList<PostModel> postModels = new ArrayList<>();

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
        floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), PostActivity.class));
            }
        });
        getPosts();
        listAdapter.setListener(new PostClickListener() {
            @Override
            public void onCLick(int position) {
                Integer id = postModels.get(0).getId();
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
        });
    }

    private void getPosts() {
        AndroidClient.getClient().getAllPost().enqueue(new Callback<ArrayList<PostModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                if (response.isSuccessful()){
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