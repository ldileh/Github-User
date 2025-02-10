package com.ldileh.githubuser.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ldileh.githubuser.data.local.entity.RepoEntity

@Dao
interface RepoDao {

    @Query("SELECT * FROM repos WHERE owner = :user ORDER BY stars DESC")
    fun getUserRepos(user: String): PagingSource<Int, RepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoEntity>)

    @Query("DELETE FROM repos WHERE owner = :user")
    suspend fun clearRepos(user: String)
}
