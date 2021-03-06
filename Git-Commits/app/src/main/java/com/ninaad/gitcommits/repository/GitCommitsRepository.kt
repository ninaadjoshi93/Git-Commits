package com.ninaad.gitcommits.repository

import com.ninaad.gitcommits.api.GitHubAPI
import com.ninaad.gitcommits.model.GitResponseItem
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitCommitsRepository @Inject constructor(private val gitHubAPI: GitHubAPI){

    suspend fun getGitCommits(owner: String, repository: String): List<GitResponseItem> {
        return withTimeout(5_000) {
            gitHubAPI.getCommits(owner, repository)
        }
    }
}