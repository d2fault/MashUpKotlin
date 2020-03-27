package com.d2fault.simplegithub.ui.api

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideGithubApi(context: Context): GithubApi
        = Retrofit.Builder()
    .baseUrl("https://api.github.com/")             // 요청 URL
    .addConverterFactory(GsonConverterFactory.create())     // 파싱 데이터의 형
    .build()                                                // build
    .create(GithubApi::class.java)                          // GithubApi의 내용으로