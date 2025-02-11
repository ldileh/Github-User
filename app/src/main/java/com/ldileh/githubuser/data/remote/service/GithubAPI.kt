package com.ldileh.githubuser.data.remote.service

import com.ldileh.githubuser.models.response.RepoResponse
import com.ldileh.githubuser.models.response.User
import com.ldileh.githubuser.models.response.UserDetail
import com.ldileh.githubuser.models.response.UserSearchResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("users/{user}/repos")
    suspend fun getUserRepos(
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<RepoResponse>

    @GET("users/{user}")
    suspend fun getUserDetail(
        @Path("user") user: String
    ): ApiResponse<UserDetail>
}