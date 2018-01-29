package com.githubusersearch.view.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.githubusersearch.GithubSearch;
import com.githubusersearch.R;
import com.githubusersearch.view.adapter.UsersAdapter;
import com.githubusersearch.viewmodel.UsersSearchViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.user_list)
    RecyclerView userlist;

    @BindView(R.id.spin_kit)
    SpinKitView spinKitView;

    private UsersAdapter usersAdapter;
    private UsersSearchViewModel usersSearchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initButterKnife();
        initDagger();
        setAdapter();
    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        userlist.setLayoutManager(linearLayoutManager);
        usersAdapter = new UsersAdapter();
        userlist.setAdapter(usersAdapter);

        usersSearchViewModel = ViewModelProviders.of(this,
                viewModelFactory).get(UsersSearchViewModel.class);

        observeViewModel();
    }

    private void observeViewModel() {
        usersSearchViewModel.contactListLiveData.observe(this, contacts -> {
        });
    }

    public void initDagger() {
        ((GithubSearch)getApplicationContext()).getMyComponent().inject(this);
    }

    public void initButterKnife() {
        ButterKnife.bind(this);
    }
}
