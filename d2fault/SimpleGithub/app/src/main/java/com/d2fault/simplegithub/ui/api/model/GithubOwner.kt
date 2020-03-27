package com.d2fault.simplegithub.ui.api.model

import com.google.gson.annotations.SerializedName

class GithubOwner(
    // login, avatar_url, followers_url, following_url
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("followers_url") val followersUrl: String,
    @SerializedName("following_url") val followingUrl: String
)