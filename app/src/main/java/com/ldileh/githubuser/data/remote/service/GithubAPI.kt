package com.ldileh.githubuser.data.remote.service

import com.ldileh.githubuser.models.response.User
import com.ldileh.githubuser.models.response.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = 30
    ): List<User>

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") perPage: Int = 30
    ): UserSearchResponse
}