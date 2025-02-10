package com.ldileh.githubuser.di

import android.content.Context
import com.ldileh.githubuser.data.remote.service.GithubAPI
import com.ldileh.githubuser.utils.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRemoteClient(@ApplicationContext context: Context): Retrofit =
        RetrofitClient.generate(context)

    @Provides
    @Singleton
    fun provideGithubService(retrofit: Retrofit): GithubAPI =
        retrofit.create(GithubAPI::class.java)
}