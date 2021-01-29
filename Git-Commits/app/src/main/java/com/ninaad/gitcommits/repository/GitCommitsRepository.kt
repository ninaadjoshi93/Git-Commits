package com.ninaad.gitcommits.repository

import com.ninaad.gitcommits.BuildConfig
import com.ninaad.gitcommits.api.GitHubAPI
import com.ninaad.gitcommits.model.GitResponseItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class GitCommitsRepository {
    private val gitHubAPI: GitHubAPI
    init {

        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        gitHubAPI = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(GitHubAPI::class.java)
    }


    suspend fun getGitCommits(owner: String, repository: String): List<GitResponseItem> {
        return gitHubAPI.getCommits("kubernetes", "kubernetes")
    }
}