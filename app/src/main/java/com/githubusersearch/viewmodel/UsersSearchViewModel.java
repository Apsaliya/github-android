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
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

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
                .filter(text -> {
                    Log.d("test", text + "");
                    return !TextUtils.isEmpty(text);
                })
                .distinctUntilChanged((text1, text2) -> {
                    Log.d("test", text1 + "   " + text2);
                    return text1.equalsIgnoreCase(text2);
                })
                .flatMap(text -> {
                    Log.d("apply", text + "");
                    return userRepo.searchUsers(text);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.onErrorReturn(throwable -> {
                    states.setState(States.ERROR);
                    states.setErrorThrowable(throwable);
                    statesLiveData.setValue(states);
                    return new UserResponse(null, null, null);
                })*/
                .doOnComplete(() -> {
                    states.setState(States.LOADING_FINISHED);
                    statesLiveData.setValue(states);
                })
                .subscribe(result -> {
                    if (result.getItems() != null) {
                        userListLiveData.setValue(result.getItems());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        states.setState(States.ERROR);
                        states.setErrorThrowable(throwable);
                        statesLiveData.setValue(states);
                    }
                }));
    }
}
