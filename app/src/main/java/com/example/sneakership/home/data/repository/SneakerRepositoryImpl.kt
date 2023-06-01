package com.example.sneakership.home.data.repository

import com.example.sneakership.home.data.remote.ISneakerApi
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.home.domain.repository.ISneakerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SneakerRepositoryImpl @Inject constructor(
    private val api: ISneakerApi
) : ISneakerRepository {

    override fun getSneakers(): Flow<List<Sneaker>> =
        flow { emit(api.getSneakers()) }
            .map { it.record }
            .flowOn(Dispatchers.IO)

    override fun getSneakersById(id: String): Flow<Sneaker?> =
        flow { emit(api.getSneakers()) }
            .map { r -> r.record }
            .map { sneakers -> sneakers.firstOrNull { it.id == id } }
            .flowOn(Dispatchers.IO)
}