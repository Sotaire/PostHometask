package com.example.posthometask.interfaces;

import com.example.posthometask.data.models.PostModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AndroidApi {

    @GET("posts/")
    Call<ArrayList<PostModel>> getAllPost();
    
    @POST("posts/")
    Call<PostModel> postData(@Body PostModel postModel);
    
    @DELETE("posts/{postId}")
    Call<PostModel> deleteData(@Path("postId")Integer postId);

    @GET("posts?users")
    Call<ArrayList<PostModel>> getUsers();

}
