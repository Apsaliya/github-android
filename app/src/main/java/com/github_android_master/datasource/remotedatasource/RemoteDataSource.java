package com.github_android_master.datasource.remotedatasource;

import com.github_android_master.datasource.DataSource;
import com.github_android_master.network.GithubService;

import javax.inject.Inject;
import javax.inject.Singleton;

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

}
