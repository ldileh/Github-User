package com.ldileh.githubuser.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ldileh.githubuser.data.local.AppDatabase
import com.ldileh.githubuser.data.local.entity.UserEntity
import com.ldileh.githubuser.data.paging.GitHubPagingSource
import com.ldileh.githubuser.data.paging.mediator.UserRemoteMediator
import com.ldileh.githubuser.data.remote.service.GithubAPI
import com.ldileh.githubuser.models.response.User
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GithubRepositories {

    fun getUsers(): Flow<PagingData<UserEntity>>
}

class GithubRepositoriesImpl @Inject constructor(
    private val api: GithubAPI,
    private val database: AppDatabase
) : GithubRepositories {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(): Flow<PagingData<UserEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = UserRemoteMediator(api, database),
            pagingSourceFactory = { database.userDao().getUsers() }
        ).flow
    }

}