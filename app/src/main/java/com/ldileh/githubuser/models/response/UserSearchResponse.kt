package com.ldileh.githubuser.models.response

import androidx.annotation.Keep

@Keep
data class UserSearchResponse(
    val items: List<User>
)