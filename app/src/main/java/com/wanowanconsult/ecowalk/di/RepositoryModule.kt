package com.wanowanconsult.ecowalk.di

import com.wanowanconsult.ecowalk.data.repository.ActivityRepositoryImpl
import com.wanowanconsult.ecowalk.domain.repository.ActivityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindActivityRepository(
        activityRepositoryImpl: ActivityRepositoryImpl
    ): ActivityRepository
}