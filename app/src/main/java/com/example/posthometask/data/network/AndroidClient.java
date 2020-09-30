package com.example.posthometask.data.network;

import com.example.posthometask.interfaces.AndroidApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AndroidClient {

    private static AndroidApi androidApi;

    public static AndroidApi getClient(){
        if (androidApi == null){
           androidApi = retrofitBuilder();
        }else {
            return androidApi;
        }
        return androidApi;
    }

    public static AndroidApi retrofitBuilder(){
        return new Retrofit.Builder()
                .baseUrl("https://android-3-mocker.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(AndroidApi.class);
    }
}
