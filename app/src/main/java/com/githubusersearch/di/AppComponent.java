package com.githubusersearch.di;

import android.app.Application;

import com.githubusersearch.GithubSearch;
import com.githubusersearch.view.ui.SearchActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by ankit on 29/01/18.
 */
@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(GithubSearch githubSearch);
    void inject(SearchActivity searchActivity);
}
