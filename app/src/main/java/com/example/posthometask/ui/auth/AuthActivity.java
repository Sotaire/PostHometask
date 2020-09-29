package com.example.posthometask.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.AuthModel;
import com.example.posthometask.R;
import com.example.posthometask.data.local.PreferenceUtils;
import com.example.posthometask.data.network.AuthClient;
import com.example.posthometask.ui.main.MainActivity;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    EditText userLogin,userPassword;
    Button singIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        userLogin = findViewById(R.id.auth_login_et);
        userPassword = findViewById(R.id.auth_password_et);
        singIn = findViewById(R.id.sing_in);

        signIn();
    }

    private void signIn() {
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = userLogin.getText().toString();
                String password = userPassword.getText().toString();

                String id = Credentials.basic(login,password);

                AuthClient.getClient().getUser(id).enqueue(new Callback<AuthModel>() {
                    @Override
                    public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                        if (response.isSuccessful() && response.body() != null){
                            PreferenceUtils.saveUserName(response.body().getLogin());
                            Log.d("Fail",response.body().getLogin());
                            startActivity(new Intent(AuthActivity.this, MainActivity.class));
                        }
                    }
                    @Override
                    public void onFailure(Call<AuthModel> call, Throwable t) {
                        Log.d("Fail","FAIL: " + t.getMessage());
                        t.printStackTrace();
                    }
                });
            }
        });

    }

}