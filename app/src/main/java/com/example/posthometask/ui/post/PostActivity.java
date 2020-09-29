package com.example.posthometask.ui.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.posthometask.R;
import com.example.posthometask.data.models.PostModel;
import com.example.posthometask.data.network.AndroidClient;
import com.example.posthometask.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    EditText title,content,user,group;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        user = findViewById(R.id.user);
        group = findViewById(R.id.group);
        button = findViewById(R.id.button_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostModel postModel = new PostModel(title.getText().toString(),
                        content.getText().toString(),
                        Integer.parseInt(String.valueOf(user.getText())),
                        Integer.parseInt(group.getText().toString()));
                AndroidClient.getClient().postData(postModel).enqueue(new Callback<PostModel>() {
                    @Override
                    public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                        if (response.isSuccessful()){
                            startActivity(new Intent(PostActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModel> call, Throwable t) {

                    }
                });
            }
        });
    }
}