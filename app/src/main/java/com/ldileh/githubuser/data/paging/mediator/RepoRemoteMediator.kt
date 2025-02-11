package com.ldileh.githubuser.data.paging.mediator

import androidx.paging.*
import androidx.room.withTransaction
import com.ldileh.githubuser.data.local.AppDatabase
import com.ldileh.githubuser.data.local.entity.RepoEntity
import com.ldileh.githubuser.data.remote.service.GithubAPI
import com.ldileh.githubuser.models.response.toEntity

@ExperimentalPagingApi
class RepoRemoteMediator(
    private val username: String,
    private val apiService: GithubAPI, // Your Retrofit API service
    private val repoDatabase: AppDatabase // Your Room database
) : RemoteMediator<Int, RepoEntity>() {

    private val repoDao = repoDatabase.repoDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1 // Start from page 1 when refreshing
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    if (state.lastItemOrNull() == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    // Get next page number (state.pages.size is incorrect, we increment manually)
                    val nextPage = (state.anchorPosition?.div(state.config.pageSize) ?: 0) + 2
                    nextPage
                }
            }

            // Call API
            val response = apiService.getUserRepos(username, page, state.config.pageSize)

            // Stop pagination when no data is returned
            val endOfPaginationReached = response.isEmpty()

            repoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    repoDao.clearRepos(username) // Clear cache on refresh
                }
                repoDao.insertRepos(response.map { it.toEntity(username) }) // Save to database
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
