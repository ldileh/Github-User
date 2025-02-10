package com.ldileh.githubuser.data.remote.service

import com.ldileh.githubuser.models.response.User
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int?,  // Last user ID (null for first page)
        @Query("per_page") perPage: Int = 30 // Number of users per page
    ): List<User>
}