package com.ldileh.githubuser.utils

fun String?.safe() = this ?: ""

fun Int?.safe() = this ?: 0