package com.githubusersearch.datasource;

import com.githubusersearch.network.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by ankit on 29/01/18.
 */

public interface DataSource {
    @GET("/users/search")
    Single<List<User>> getUsersList();
}
