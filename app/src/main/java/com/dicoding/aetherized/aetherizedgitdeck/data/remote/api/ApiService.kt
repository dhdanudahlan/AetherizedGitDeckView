package com.dicoding.aetherized.aetherizedgitdeck.data.remote.api

import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.dicoding.aetherized.aetherizedgitdeck.data.UsersResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") perPage: Int
    ): Call<UsersResponse>

    @GET("users")
    fun findUsers(
        @Query("per_page") perPage: Int = 20
    ): Call<List<User>>

    @GET("users/{username}")
    fun getUserDetails(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}