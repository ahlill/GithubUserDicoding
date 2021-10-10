package com.example.githubuser.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiSearch {
    @GET("search/users")
    fun getSearches(@Query("q") username: String?): Call<DataModel>
}

interface ApiFollowers {
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String?): Call<List<ItemsItem>>
}

interface ApiFollowings {
    @GET("users/{username}/following")
    fun getFollowings(@Path("username") username: String?): Call<List<ItemsItem>>
}