package com.wanowanconsult.ecowalk.di

import android.app.Application
import androidx.room.Room
import com.wanowanconsult.ecowalk.data.local.EcowalkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}