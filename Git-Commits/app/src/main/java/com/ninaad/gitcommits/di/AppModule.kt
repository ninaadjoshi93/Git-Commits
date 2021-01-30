package com.ninaad.gitcommits.di

import com.ninaad.gitcommits.BuildConfig
import com.ninaad.gitcommits.api.GitHubAPI
import com.ninaad.gitcommits.repository.GitCommitsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    companion object {

        @Singleton
        @Provides
        fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Singleton
        @Provides
        fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) : OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

        @Singleton
        @Provides
        fun provideRetrofitInstance(client: OkHttpClient) : GitHubAPI {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build()
                .create(GitHubAPI::class.java)
        }

        @Singleton
        @Provides
        fun provideGitHubRepository(gitHubAPI: GitHubAPI) : GitCommitsRepository {
            return GitCommitsRepository(gitHubAPI)
        }
    }
}