package com.ldileh.githubuser.models.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("html_url")
    val htmlUrl: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("login")
    val login: String?,
    @SerializedName("organizations_url")
    val organizationsUrl: String?,
    @SerializedName("repos_url")
    val reposUrl: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("user_view_type")
    val userViewType: String?
)