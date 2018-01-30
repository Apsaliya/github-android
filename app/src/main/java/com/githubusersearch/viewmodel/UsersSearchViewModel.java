package com.githubusersearch.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.githubusersearch.datasource.UserRepo;
import com.githubusersearch.network.model.User;
import com.githubusersearch.network.model.UserResponse;
import com.githubusersearch.view.ui.States;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.io.InterruptedIOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Notification;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by ankit on 29/01/18.
 */

public class UsersSearchViewModel extends AndroidViewModel {
    private UserRepo userRepo;
    private States states = new States();
    public final MutableLiveData<List<User>> userListLiveData = new MutableLiveData<>();
    public final MutableLiveData<States> statesLiveData = new MutableLiveData<>();

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

    public void search(Flowable<String> sequenceFlowable) {
        compositeDisposable.add(sequenceFlowable
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(text -> !TextUtils.isEmpty(text))
                .distinctUntilChanged(String::equalsIgnoreCase)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .switchMap(text -> userRepo.searchUsers(text))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    states.setState(States.LOADING_FINISHED);
                    statesLiveData.setValue(states);
                })
                .subscribe(userResponse -> {
                    if (userResponse != null && userResponse.getItems() != null) {
                        userListLiveData.setValue(userResponse.getItems());
                    }
                }, throwable -> {
                    states.setState(States.ERROR);
                    statesLiveData.setValue(states);
                }));
    }
}
