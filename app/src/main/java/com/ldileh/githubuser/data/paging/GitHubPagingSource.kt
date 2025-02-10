package com.ldileh.githubuser.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ldileh.githubuser.data.remote.service.GithubAPI
import com.ldileh.githubuser.models.response.User

class GitHubPagingSource(private val api: GithubAPI) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val since = params.key ?: 0 // Start from user ID 0
            val response = api.getUsers(since, params.loadSize)

            LoadResult.Page(
                data = response,
                prevKey = null, // No previous page (GitHub API doesn't support backward pagination)
                nextKey = response.lastOrNull()?.id // Use last user's ID for next page
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null // No need for refresh key in this case
    }
}