package com.ninaad.gitcommits.di

import com.ninaad.gitcommits.application.GitApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        BindingModule::class
    ]
)
interface AppComponent: AndroidInjector<GitApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: GitApplication): Builder

        fun build(): AppComponent
    }

//    @Component.Factory
//    interface Factory {
//        fun create(@BindsInstance context: Context) : AppComponent
//    }
}