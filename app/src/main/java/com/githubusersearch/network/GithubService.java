package com.githubusersearch.network;

import com.githubusersearch.network.model.User;
import com.githubusersearch.network.model.UserResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ankit on 29/01/18.
 */

public interface GithubService {
    @GET("/search/users")
    Single<UserResponse> getContactList(@Query("q") String text, @Query("sort") String sort_by);
}
