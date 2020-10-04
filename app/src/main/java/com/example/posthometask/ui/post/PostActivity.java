package com.example.posthometask.ui.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.posthometask.R;
import com.example.posthometask.data.models.PostModel;
import com.example.posthometask.data.network.AndroidClient;
import com.example.posthometask.ui.list.ListFragment;
import com.example.posthometask.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    EditText title, content, user, group;
    Button button;
    private static final int MY_ID = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        user = findViewById(R.id.user);
        group = findViewById(R.id.group);
        button = findViewById(R.id.button_send);
        getPost();
        int intentID = getIntent().getIntExtra(ListFragment.POST_KEY, 0);
        int position = getIntent().getIntExtra(ListFragment.UPDATE_KEY, 0);
        String titleS = getIntent().getStringExtra("title");
        String contentS = getIntent().getStringExtra("content");
        int groupS = getIntent().getIntExtra("group", 0);
        int idS = getIntent().getIntExtra("id", 0);
        if (intentID == ListFragment.UPDATE) {
            button.setText("update");
            title.setText(titleS);
            content.setText(contentS);
            user.setText(String.valueOf(idS));
            group.setText(String.valueOf(groupS));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentID == ListFragment.UPDATE) {
                    Log.d("update", "1");
                    Log.d("update", String.valueOf(position));
                    PostModel postModel = new PostModel(title.getText().toString(),
                            content.getText().toString(),
                            MY_ID,
                            Integer.parseInt(group.getText().toString()));
                    AndroidClient.getClient().updatePost(position, postModel).enqueue(new Callback<PostModel>() {
                        @Override
                        public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                            if (response.isSuccessful()) {
                                Log.d("update", "updated");
                                startActivity(new Intent(PostActivity.this, MainActivity.class));
                            }
                        }

                        @Override
                        public void onFailure(Call<PostModel> call, Throwable t) {
                            Log.d("update", t.getMessage());
                            Log.d("update", t.getLocalizedMessage());
                        }
                    });
                } else if (intentID == ListFragment.POST) {
                    Log.d("send", "1");
                    PostModel postModel = new PostModel(title.getText().toString(),
                            content.getText().toString(),
                            MY_ID,
                            Integer.parseInt(group.getText().toString()));
                    AndroidClient.getClient().postData(postModel).enqueue(new Callback<PostModel>() {
                        @Override
                        public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                            if (response.isSuccessful()) {
                                Log.d("send", "send");
                                startActivity(new Intent(PostActivity.this, MainActivity.class));
                            }
                        }

                        @Override
                        public void onFailure(Call<PostModel> call, Throwable t) {
                            Log.d("Failure",t.getLocalizedMessage());
                        }
                    });
                }

            }
        });
    }

    private void getPost() {
    }
}