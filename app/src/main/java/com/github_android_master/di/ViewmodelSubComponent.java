package com.github_android_master.di;

import com.github_android_master.viewmodel.UsersSearchViewModel;

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
