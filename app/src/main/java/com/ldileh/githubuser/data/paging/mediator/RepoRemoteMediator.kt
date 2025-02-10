package com.ldileh.githubuser.data.paging.mediator

import androidx.paging.*
import androidx.room.withTransaction
import com.ldileh.githubuser.data.local.AppDatabase
import com.ldileh.githubuser.data.local.entity.RepoEntity
import com.ldileh.githubuser.data.remote.service.GithubAPI
import com.ldileh.githubuser.models.response.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class RepoRemoteMediator(
    private val user: String,
    private val api: GithubAPI,
    private val database: AppDatabase
) : RemoteMediator<Int, RepoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    (state.pages.size) + 1
                }
            }

            val response = api.getUserRepos(user, perPage = state.config.pageSize, page = page)

            withContext(Dispatchers.IO) {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.repoDao().clearRepos(user)
                    }
                    database.repoDao().insertRepos(response.map { it.toEntity(user) })
                }
            }

            MediatorResult.Success(endOfPaginationReached = response.isEmpty())

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
