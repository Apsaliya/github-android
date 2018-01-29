package com.githubusersearch.view.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.githubusersearch.GithubSearch;
import com.githubusersearch.R;
import com.githubusersearch.view.RxSearchObservable;
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

    @BindView(R.id.clear_search)
    ImageView searchClear;

    @BindView(R.id.searchbar)
    AppCompatEditText searchBar;

    private UsersAdapter usersAdapter;
    private UsersSearchViewModel usersSearchViewModel;
    private RxSearchObservable rxSearchObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initButterKnife();
        initDagger();
        setAdapter();
        spinKitView.setVisibility(View.GONE);
        initSearchBar();
    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        userlist.setLayoutManager(linearLayoutManager);
        usersAdapter = new UsersAdapter(this);
        userlist.setAdapter(usersAdapter);

        usersSearchViewModel = ViewModelProviders.of(this,
                viewModelFactory).get(UsersSearchViewModel.class);

        observeViewModel();
    }

    private void initSearchBar() {
        if (rxSearchObservable == null) {
            rxSearchObservable = new RxSearchObservable();
        }

        searchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setText(null);
                usersAdapter.updateData(null);
            }
        });

        usersSearchViewModel.search(RxSearchObservable.fromView(searchBar));
    }
    private void observeViewModel() {
        usersSearchViewModel.userListLiveData.observe(this, contacts -> {
            Log.d("live data change", "data change");
            usersAdapter.updateData(usersSearchViewModel.userListLiveData.getValue());
        });
    }

    public void initDagger() {
        ((GithubSearch)getApplication()).getMyComponent().inject(this);
    }

    public void initButterKnife() {
        ButterKnife.bind(this);
    }
}
