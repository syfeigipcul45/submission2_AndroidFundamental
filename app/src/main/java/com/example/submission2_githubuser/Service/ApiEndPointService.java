package com.example.submission2_githubuser.Service;

import com.example.submission2_githubuser.model.DetailUserModel;
import com.example.submission2_githubuser.model.ResponseFollowersModelItem;
import com.example.submission2_githubuser.model.ResponseFollowingModelItem;
import com.example.submission2_githubuser.model.UserSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPointService {
    @GET("search/users")
    Call<UserSearch> getUserSearch(@Query("q") String username);

    @GET("users/{username}")
    Call<DetailUserModel> getDetailUser(@Path("username") String username);

    @GET("users/{username}/followers")
    Call<List<ResponseFollowersModelItem>> getFollowers(@Path("username") String username);

    @GET("users/{username}/following")
    Call<List<ResponseFollowingModelItem>> getFollowing(@Path("username") String username);
}
