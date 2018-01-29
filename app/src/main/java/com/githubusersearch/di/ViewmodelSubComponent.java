package com.githubusersearch.di;

import com.githubusersearch.viewmodel.UsersSearchViewModel;

import dagger.Subcomponent;

/**
 * Created by ankit on 29/01/18.
 */
@Subcomponent
public interface ViewmodelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewmodelSubComponent build();
    }

    UsersSearchViewModel usersSearchViewModel();
}
