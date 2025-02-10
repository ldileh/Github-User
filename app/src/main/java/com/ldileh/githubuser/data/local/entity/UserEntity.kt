package com.ldileh.githubuser.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String?,
    val organizationsUrl: String?,
    val reposUrl: String?,
    val url: String?,
    val userViewType: String?
): Serializable