package com.ninaad.gitcommits.di

import com.ninaad.gitcommits.ui.activity.GitCommitsActivity
import com.ninaad.gitcommits.ui.fragment.GitCommitsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun bindsGitCommitsActivity(): GitCommitsActivity

    @ContributesAndroidInjector
    abstract fun bindGitCommitsFragment(): GitCommitsFragment
}