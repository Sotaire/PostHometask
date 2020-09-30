package com.example.posthometask.ui.user;

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
import com.example.posthometask.adapters.UsersAdapter;
import com.example.posthometask.data.models.PostModel;
import com.example.posthometask.data.network.AndroidClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    ArrayList<PostModel> users;
    RecyclerView recyclerView;
    UsersAdapter usersAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.users_recycler);
        usersAdapter = new UsersAdapter();
        recyclerView.setAdapter(usersAdapter);
        getUsers();
    }

    private void getUsers() {
        AndroidClient.getClient().getUsers().enqueue(new Callback<ArrayList<PostModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                if (response.isSuccessful()){
                    users = new ArrayList<>();
                    users = response.body();
                    usersAdapter.setPostModels(users);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                Log.d("users",t.getMessage());
            }
        });
    }
}