package com.example.sneakership.cart.di

import com.example.sneakership.cart.data.local.ISneakerDao
import com.example.sneakership.cart.data.repository.CartRepositoryImpl
import com.example.sneakership.cart.domain.repository.ICartRepository
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
object CartModule {

    @Provides
    fun provideCartRepository(
        dao: ISneakerDao
    ) : ICartRepository =
        CartRepositoryImpl(dao)


}