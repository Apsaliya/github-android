package com.githubusersearch.datasource;

import com.githubusersearch.datasource.remotedatasource.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

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

}
