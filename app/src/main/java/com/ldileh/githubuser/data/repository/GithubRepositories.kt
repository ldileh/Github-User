package com.ldileh.githubuser.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ldileh.githubuser.data.local.AppDatabase
import com.ldileh.githubuser.data.local.entity.RepoEntity
import com.ldileh.githubuser.data.local.entity.UserEntity
import com.ldileh.githubuser.data.paging.UserSearchPagingSource
import com.ldileh.githubuser.data.paging.mediator.RepoRemoteMediator
import com.ldileh.githubuser.data.paging.mediator.UserRemoteMediator
import com.ldileh.githubuser.data.remote.service.GithubAPI
import com.ldileh.githubuser.models.response.UserDetail
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GithubRepositories {

    fun getUsers(query: String): Flow<PagingData<UserEntity>>
    fun getUserRepos(username: String): Flow<PagingData<RepoEntity>>
    suspend fun getUserDetail(username: String): ApiResponse<UserDetail>
}

class GithubRepositoriesImpl @Inject constructor(
    private val api: GithubAPI,
    private val database: AppDatabase
) : GithubRepositories {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(query: String): Flow<PagingData<UserEntity>> {
        val pagingSourceFactory = {
            if (query.isEmpty()) database.userDao().getAllUsers()
            else database.userDao().searchUsers(query)
        }

        return if (query.isEmpty()) {
            Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                remoteMediator = UserRemoteMediator(api, database),
                pagingSourceFactory = pagingSourceFactory
            ).flow
        } else {
            Pager(
                config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                pagingSourceFactory = { UserSearchPagingSource(api, query) }
            ).flow
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getUserRepos(username: String): Flow<PagingData<RepoEntity>> {
        val pagingSourceFactory = { database.repoDao().getUserRepos(username) }

        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = RepoRemoteMediator(username, api, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getUserDetail(username: String): ApiResponse<UserDetail> {
        return api.getUserDetail(username)
    }

}