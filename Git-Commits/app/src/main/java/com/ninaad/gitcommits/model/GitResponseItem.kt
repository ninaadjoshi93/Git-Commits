package com.ninaad.gitcommits.model

import com.google.gson.annotations.SerializedName

data class GitResponseItem(
    @SerializedName("commit")
    val commit: Commit,
    @SerializedName("sha")
    val hash: String,
    @SerializedName("author")
    val author: Author
)

data class Commit(
    @SerializedName("message")
    val message: String,
    @SerializedName("author")
    val author: CommitAuthor
)

data class CommitAuthor(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("date")
    val date: String
)

data class Author(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)