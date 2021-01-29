package com.ninaad.gitcommits.api

import com.ninaad.gitcommits.model.GitResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubAPI {
    @GET("/repos/{owner}/{repo}/commits")
    suspend fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<GitResponseItem>
}