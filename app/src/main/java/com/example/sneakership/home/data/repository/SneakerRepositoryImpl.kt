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

    override fun getSneakers(q: String): Flow<List<Sneaker>> =
        flow { emit(api.getSneakers()) }
            .map { it.record }
            .map { it.filter { item -> item.name.contains(q, ignoreCase = true) } }
            .flowOn(Dispatchers.IO)

    override fun getSneakersById(id: String): Flow<Sneaker?> =
        flow { emit(api.getSneakers()) }
            .map { r -> r.record }
            .map { sneakers -> sneakers.firstOrNull { it.id == id } }
            .flowOn(Dispatchers.IO)

    override fun getSneakersByPriceLowest(q: String): Flow<List<Sneaker>> =
        flow { emit(api.getSneakers()) }
            .map { r -> r.record }
            .map { it.filter { item -> item.name.contains(q, ignoreCase = true) } }
            .map { sneakers -> sneakers.sortedBy { it.retailPrice }}
            .flowOn(Dispatchers.IO)

    override fun getSneakersByPriceHighest(q: String): Flow<List<Sneaker>> =
        flow { emit(api.getSneakers()) }
            .map { r -> r.record }
            .map { it.filter { item -> item.name.contains(q, ignoreCase = true) } }
            .map { sneakers -> sneakers.sortedByDescending { it.retailPrice }}
            .flowOn(Dispatchers.IO)
}