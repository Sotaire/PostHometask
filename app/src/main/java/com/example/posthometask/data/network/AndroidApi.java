package com.example.posthometask.data.network;

import com.example.posthometask.data.models.PostModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @PUT("posts/{id}")
    Call<PostModel> updatePost(@Path("id")Integer id,
                               @Body PostModel postModel);

    @GET("posts?")
    Call<ArrayList<PostModel>> getUser(@Query("user") Integer id);

}
