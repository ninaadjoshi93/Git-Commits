package com.ninaad.gitcommits.repository

import com.ninaad.gitcommits.api.GitHubAPI
import com.ninaad.gitcommits.model.GitResponseItem
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


class GitCommitsRepository @Inject constructor(private val gitHubAPI: GitHubAPI){

    suspend fun getGitCommits(owner: String, repository: String): List<GitResponseItem> {
        try {
            return withTimeout(5_000) {
                gitHubAPI.getCommits(owner, repository)
            }
        } catch (cause: Throwable) {
            throw NetworkRequestError("Unable to get Git commits", cause)
        }
    }
}

class NetworkRequestError(message: String, cause: Throwable?) : Throwable(message, cause)