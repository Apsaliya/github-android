package com.github_android_master.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.github_android_master.datasource.UserRepo;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ankit on 29/01/18.
 */

public class UsersSearchViewModel extends AndroidViewModel {
    private UserRepo userRepo;
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
