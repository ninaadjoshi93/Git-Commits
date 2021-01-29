package com.ninaad.gitcommits.model

import com.google.gson.annotations.SerializedName

data class GitResponseItem(
    @SerializedName("commit")
    val commit: Commit,
    @SerializedName("sha")
    val hash: String
)

data class Commit(
    @SerializedName("message")
    val message: String,
    @SerializedName("author")
    val author: Author
)

data class Author(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("date")
    val date: String
)