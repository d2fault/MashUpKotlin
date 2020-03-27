package com.d2fault.simplegithub.ui.api.model

import com.google.gson.annotations.SerializedName

class GithubOwner(
    // login, avatar_url, followers_url, following_url
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("followers_url") val followersUrl: String,
    @SerializedName("following_url") val followingUrl: String)
//    "login": "chvin",
//    "avatar_url": "https://avatars2.githubusercontent.com/u/5383506?v=4",
//    "url": "https://api.github.com/users/chvin",
//    "html_url": "https://github.com/chvin",
//    "followers_url": "https://api.github.com/users/chvin/followers",
//    "following_url": "https://api.github.com/users/chvin/following{/other_user}",
//    "repos_url": "https://api.github.com/users/chvin/repos",
//    "events_url": "https://api.github.com/users/chvin/events{/privacy}",
//    "received_events_url": "https://api.github.com/users/chvin/received_events",
//    "type": "User",
//    "site_admin": false