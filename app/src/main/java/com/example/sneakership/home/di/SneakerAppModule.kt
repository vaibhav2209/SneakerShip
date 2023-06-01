package com.example.sneakership.home.di

import com.example.sneakership.home.data.remote.ISneakerApi
import com.example.sneakership.home.data.repository.SneakerRepositoryImpl
import com.example.sneakership.home.domain.repository.ISneakerRepository
import com.example.sneakership.utils.network.ApiEndPoints
import com.example.sneakership.utils.network.RetrofitContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object SneakerAppModule {

    @Provides
    fun provideSneakerApi(): ISneakerApi = run {
        RetrofitContainer.getRetrofitBuilder(ApiEndPoints.BASE_URL)
            .build().create(ISneakerApi::class.java)
    }

    @Provides
    fun provideSneakerRepository(
        api: ISneakerApi
    ) : ISneakerRepository =
        SneakerRepositoryImpl(api)


}