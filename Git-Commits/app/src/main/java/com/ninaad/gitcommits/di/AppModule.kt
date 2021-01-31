package com.ninaad.gitcommits.di

import android.content.Context
import com.ninaad.gitcommits.BuildConfig
import com.ninaad.gitcommits.api.GitHubAPI
import com.ninaad.gitcommits.repository.GitCommitsRepository
import com.ninaad.gitcommits.util.NetworkUtil
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    companion object {

        @JvmStatic
        @Singleton
        @Provides
        fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) : OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideRetrofitInstance(client: OkHttpClient) : GitHubAPI {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(GitHubAPI::class.java)
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideGitHubRepository(gitHubAPI: GitHubAPI) : GitCommitsRepository {
            return GitCommitsRepository(gitHubAPI)
        }

        @JvmStatic
        @Singleton
        @Provides
        fun provideNetworkUtilInstance(context: Context) : NetworkUtil {
            return NetworkUtil(context)
        }
    }
}