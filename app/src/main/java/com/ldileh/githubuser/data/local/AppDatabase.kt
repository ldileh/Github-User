package com.ldileh.githubuser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ldileh.githubuser.data.local.dao.RepoDao
import com.ldileh.githubuser.data.local.dao.UserDao
import com.ldileh.githubuser.data.local.entity.RepoEntity
import com.ldileh.githubuser.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, RepoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repoDao(): RepoDao
}