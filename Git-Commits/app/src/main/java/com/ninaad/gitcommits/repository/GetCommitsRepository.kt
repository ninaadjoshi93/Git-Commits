package com.ninaad.gitcommits.repository

import com.ninaad.gitcommits.api.GitHubAPI
import com.ninaad.gitcommits.model.GitResponseItem
import javax.inject.Inject


class GetCommitsRepository{
    @Inject
    lateinit var gitHubAPI: GitHubAPI

    suspend fun getGitCommits(owner: String, repository: String): List<GitResponseItem> {
        return gitHubAPI.getCommits("kubernetes", "kubernetes")
    }
}