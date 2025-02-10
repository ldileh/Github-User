package com.ldileh.githubuser.data.paging.mediator

import androidx.paging.*
import androidx.room.withTransaction
import com.ldileh.githubuser.data.local.AppDatabase
import com.ldileh.githubuser.data.local.entity.UserEntity
import com.ldileh.githubuser.data.remote.service.GithubAPI
import com.ldileh.githubuser.models.response.User
import com.ldileh.githubuser.utils.safe
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val api: GithubAPI,
    private val database: AppDatabase
) : RemoteMediator<Int, UserEntity>() {

    private val userDao = database.userDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, UserEntity>): MediatorResult {
        return try {
            val since = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.id ?: return MediatorResult.Success(endOfPaginationReached = true)
            }

            val response = api.getUsers(since, state.config.pageSize)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDao.clearUsers() // Clear old data
                }
                userDao.insertUsers(response.map { it.toEntity() }) // Save new data
            }

            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}

// Extension function to map API model to Room entity
fun User.toEntity(): UserEntity = UserEntity(
    id = id.safe(),
    login = login.safe(),
    avatarUrl = avatarUrl.safe(),
    htmlUrl = htmlUrl,
    organizationsUrl = organizationsUrl,
    reposUrl = reposUrl,
    url = url,
    userViewType = userViewType,
)
