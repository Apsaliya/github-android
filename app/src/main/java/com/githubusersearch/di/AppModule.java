package com.githubusersearch.di;

import android.arch.lifecycle.ViewModelProvider;

import com.githubusersearch.network.GithubService;
import com.githubusersearch.network.NetworkConstants;
import com.githubusersearch.viewmodel.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ankit on 29/01/18.
 */

@Module(subcomponents = ViewmodelSubComponent.class)
public class AppModule {
    @Singleton
    @Provides
    GithubService provideGithubService() {

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        okHttpBuilder.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Accept", "application/json")
                    .build();

            return chain.proceed(request);
        });
        OkHttpClient okHttpClient = okHttpBuilder.build();


        return new Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(GithubService.class);
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            ViewmodelSubComponent.Builder viewModelSubComponent) {

        return new ViewModelFactory(viewModelSubComponent.build());
    }
}
