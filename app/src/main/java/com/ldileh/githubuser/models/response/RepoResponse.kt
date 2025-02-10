package com.ldileh.githubuser.models.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.ldileh.githubuser.data.local.entity.RepoEntity

@Keep
data class RepoResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("stargazers_count")
    val stargazersCount: Int
) {

    @Keep
    data class Owner(@SerializedName("login") val login: String)
}

// Convert DTO to Entity
fun RepoResponse.toEntity(user: String): RepoEntity {
    return RepoEntity(
        id = id,
        name = name,
        description = description,
        owner = user,
        stars = stargazersCount
    )
}