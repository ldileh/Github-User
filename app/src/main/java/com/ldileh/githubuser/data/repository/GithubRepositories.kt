package com.ldileh.githubuser.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ldileh.githubuser.data.local.AppDatabase
import com.ldileh.githubuser.data.local.entity.UserEntity
import com.ldileh.githubuser.data.paging.UserSearchPagingSource
import com.ldileh.githubuser.data.paging.mediator.UserRemoteMediator
import com.ldileh.githubuser.data.remote.service.GithubAPI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GithubRepositories {

    fun getUsers(query: String): Flow<PagingData<UserEntity>>
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

}