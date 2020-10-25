package com.example.posthometask.ui.auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.AuthModel;
import com.example.posthometask.R;
import com.example.posthometask.data.local.PreferenceUtils;
import com.example.posthometask.data.network.AuthClient;
import com.example.posthometask.ui.main.MainActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.AUTHORITY;

public class AuthActivity extends AppCompatActivity {

    EditText userLogin,userPassword;
    Button singIn,generate;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        userLogin = findViewById(R.id.auth_login_et);
        userPassword = findViewById(R.id.auth_password_et);
        singIn = findViewById(R.id.sing_in);
        generate = findViewById(R.id.generate_qr);
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQr();
            }
        });

        signIn();
    }

    private void scanQr() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!= null){
            id = result.getContents();
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
                    Toast.makeText(AuthActivity.this, "неверные данные", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void signIn() {
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = userLogin.getText().toString();
                String password = userPassword.getText().toString();
                String id = Credentials.basic(login,password);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,id);
                startActivity(Intent.createChooser(intent,"use qr code generator"));
//                QRCodeWriter qrCodeWriter = new QRCodeWriter();
//                try {
//                    BitMatrix matrix;
//                    if (!id.isEmpty() && id != null){
//                        matrix = qrCodeWriter.encode(id, BarcodeFormat.QR_CODE,200,200);
//                    }else{
//                        matrix = qrCodeWriter.encode("Not Found", BarcodeFormat.QR_CODE,200,200);
//                    }
//                    Bitmap bitmap = Bitmap.createBitmap(200,200,Bitmap.Config.RGB_565);
//
//                    for (int x = 0; x < 300; x++){
//                        for (int y = 0; y < 300; y++){
//                            int color;
//                            if (matrix.get(x,y)){
//                                color = Color.BLACK;
//                            }else {
//                                color = Color.WHITE ;
//                            }
//                            bitmap.setPixel(x,y,color);
//                        }
//                    }
//
//
//                }catch (Exception e){
//
//                }
//
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}