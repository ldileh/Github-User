package com.ldileh.githubuser.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ldileh.githubuser.data.local.entity.UserEntity
import com.ldileh.githubuser.data.paging.mediator.toEntity
import com.ldileh.githubuser.data.remote.service.GithubAPI

class UserSearchPagingSource(
    private val api: GithubAPI,
    private val query: String
) : PagingSource<Int, UserEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {
        return try {
            val response = api.searchUsers(query, params.loadSize)
            LoadResult.Page(
                data = response.items.map { it.toEntity() },
                prevKey = null,
                nextKey = null // GitHub search API does not provide pagination keys
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int? = null
}