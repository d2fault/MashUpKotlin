package com.d2fault.simplegithub.ui.api

import com.d2fault.simplegithub.ui.api.model.GithubRepo
import com.d2fault.simplegithub.ui.api.model.RepoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    // https://api.github.com/search/repositories?q=tetris&sort=stars&order=desc
    @GET("search/repositories")
    fun searchRepository(@Query("q") query: String): Call<RepoSearchResponse>

    // https://api.github.com/repos/chvin/react-tetris
    // /{owner}/{full_name}
    @GET("repos/{owner}/{name}")
    fun getRepository(
        // owner의 이름은 owner object 안의 login이다.
        @Path("owner") ownerLogin: String,
        @Path("name") repoName: String): Call<GithubRepo>
}