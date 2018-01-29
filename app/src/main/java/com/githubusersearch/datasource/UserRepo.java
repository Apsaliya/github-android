package com.githubusersearch.datasource;

import com.githubusersearch.datasource.remotedatasource.RemoteDataSource;
import com.githubusersearch.network.model.User;
import com.githubusersearch.network.model.UserResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by ankit on 29/01/18.
 */

@Singleton
public class UserRepo {
    private RemoteDataSource remoteDataSource;

    @Inject
    public UserRepo(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public Flowable<UserResponse> searchUsers(String text) {
        return remoteDataSource.getUsersList(text).toFlowable();
    }

}
