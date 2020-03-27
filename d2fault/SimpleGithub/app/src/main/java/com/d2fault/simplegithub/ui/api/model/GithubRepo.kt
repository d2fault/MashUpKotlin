package com.d2fault.simplegithub.ui.api.model

import com.google.gson.annotations.SerializedName

class GithubRepo(
    // name, full_name, owner, description, language, updated_at, stargazers_count
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val owner: GithubOwner,
    val description: String?,
    val language: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("stargazers_count") val stargazersCount: Int)