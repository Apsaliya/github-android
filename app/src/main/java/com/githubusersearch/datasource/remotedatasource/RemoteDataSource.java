package com.githubusersearch.datasource.remotedatasource;

import com.githubusersearch.datasource.DataSource;
import com.githubusersearch.network.GithubService;
import com.githubusersearch.network.model.User;
import com.githubusersearch.network.model.UserResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by ankit on 29/01/18.
 */

@Singleton
public class RemoteDataSource implements DataSource {
    private GithubService githubService;

    @Inject
    public RemoteDataSource(GithubService githubService) {
       this.githubService = githubService;
    }

    @Override
    public Single<UserResponse> getUsersList(String text) {
        return githubService.getContactList(text, "followers");
    }
}
