package com.github_android_master;

import android.app.Application;

import com.github_android_master.di.AppComponent;
import com.github_android_master.di.DaggerAppComponent;

/**
 * Created by ankit on 29/01/18.
 */

public class GithubSearch extends Application {
    private AppComponent myComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        myComponent = DaggerAppComponent.builder().application(this)
                .build();
        myComponent.inject(this);
    }

    public AppComponent getMyComponent() {
        return myComponent;
    }
}
