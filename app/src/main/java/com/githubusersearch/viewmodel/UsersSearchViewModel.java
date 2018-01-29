package com.githubusersearch.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.githubusersearch.datasource.UserRepo;
import com.githubusersearch.network.model.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ankit on 29/01/18.
 */

public class UsersSearchViewModel extends AndroidViewModel {
    private UserRepo userRepo;
    public final MutableLiveData<List<User>> contactListLiveData = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public UsersSearchViewModel(@NonNull UserRepo userRepo, @NonNull Application application) {
        super(application);
        this.userRepo = userRepo;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
