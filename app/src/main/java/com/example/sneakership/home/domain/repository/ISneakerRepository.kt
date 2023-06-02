package com.example.sneakership.home.domain.repository

import com.example.sneakership.home.domain.model.Sneaker
import kotlinx.coroutines.flow.Flow

interface ISneakerRepository {

    fun getSneakers(q: String): Flow<List<Sneaker>>

    fun getSneakersById(id: String): Flow<Sneaker?>
    fun getSneakersByPriceLowest(q: String): Flow<List<Sneaker>>
    fun getSneakersByPriceHighest(q: String): Flow<List<Sneaker>>
}