package com.example.sneakership.application

import android.content.Context
import androidx.room.Room
import com.example.sneakership.cart.data.local.ISneakerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext app: Context
    ): AppDatabase =
        Room
            .databaseBuilder(
                app,
                AppDatabase::class.java,
                AppDatabase.ROOM_DATABASE
            )
            .build()

    @Provides
    @Singleton
    fun provideSneakerDao(db: AppDatabase) : ISneakerDao =
        db.sneakerDao()
}