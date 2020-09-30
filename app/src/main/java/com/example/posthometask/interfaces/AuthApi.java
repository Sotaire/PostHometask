package com.example.posthometask.interfaces;

import com.example.AuthModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi {

    @GET("user")
    Call<AuthModel> getUser(@Header("Authorization") String id);

}
