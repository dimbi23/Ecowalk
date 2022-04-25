package com.wanowanconsult.ecowalk.di

import android.app.Activity
import android.app.Application
import androidx.room.Room
import com.wanowanconsult.ecowalk.data.local.EcowalkDatabase
import com.wanowanconsult.ecowalk.domain.repository.ActivityRepository
import com.wanowanconsult.ecowalk.framework.MainActivity
import com.wanowanconsult.ecowalk.framework.manager.LocationProvider
import com.wanowanconsult.ecowalk.framework.manager.StepCounterManager
import com.wanowanconsult.ecowalk.presentation.activity.ActivityViewmodel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideEcoWalkDatabase(app: Application): EcowalkDatabase {
        return Room.databaseBuilder(
            app,
            EcowalkDatabase::class.java,
            "ecowalk.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLocationProvider() = LocationProvider()

    @Provides
    @Singleton
    fun provideStepCounterManager() = StepCounterManager()
}