package com.ldileh.githubuser.di

import com.ldileh.githubuser.data.repository.GithubRepositories
import com.ldileh.githubuser.data.repository.GithubRepositoriesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindGithubRepositories(impl: GithubRepositoriesImpl): GithubRepositories
}