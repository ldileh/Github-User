package com.ldileh.githubuser.utils

import android.widget.ImageView
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import com.ldileh.githubuser.R

fun ImageView.loadImage(url: String, transformation: Transformation){
    val request = ImageRequest.Builder(context)
        .data(url)
        .target(this) // Load into this ImageView
        .crossfade(true)
        .transformations(transformation)
        .placeholder(R.drawable.ic_avatar_placeholder)
        .error(R.drawable.ic_avatar_error)
        .build()

    context.imageLoader.enqueue(request)
}

fun ImageView.loadAvatar(url: String){
    loadImage(url, CircleCropTransformation())
}