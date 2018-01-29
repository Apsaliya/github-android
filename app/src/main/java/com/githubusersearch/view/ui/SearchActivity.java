package com.githubusersearch.view.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.githubusersearch.GithubSearch;
import com.githubusersearch.R;
import com.githubusersearch.view.RxSearchObservable;
import com.githubusersearch.view.adapter.UsersAdapter;
import com.githubusersearch.viewmodel.UsersSearchViewModel;

import java.io.IOException;

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
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activity = this;
        initButterKnife();
        initDagger();
        setAdapter();
        initSearchBar();
    }

    private void observeStates() {
        usersSearchViewModel.statesLiveData.observe(this, state -> {
            if (state == null) {
                Toast.makeText(activity, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                return;
            }

            if (States.ERROR == state.getState()) {
                Toast.makeText(activity, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                spinKitView.setVisibility(View.GONE);
            } else if (States.LOADING == state.getState()) {
                if (searchBar.getText() != null && !TextUtils.isEmpty(searchBar.getText().toString())) {
                    spinKitView.setVisibility(View.VISIBLE);
                    userlist.setVisibility(View.GONE);
                }
            } else if (States.LOADING_FINISHED == state.getState()) {
                spinKitView.setVisibility(View.GONE);
                userlist.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        userlist.setLayoutManager(linearLayoutManager);
        usersAdapter = new UsersAdapter(this);
        userlist.setAdapter(usersAdapter);

        usersSearchViewModel = ViewModelProviders.of(this,
                viewModelFactory).get(UsersSearchViewModel.class);

        observeViewModel();
        observeStates();
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
