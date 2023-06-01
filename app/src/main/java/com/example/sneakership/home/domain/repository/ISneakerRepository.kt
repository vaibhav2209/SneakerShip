package com.example.sneakership.home.domain.repository

import com.example.sneakership.home.domain.model.Sneaker
import kotlinx.coroutines.flow.Flow

interface ISneakerRepository {

    fun getSneakers(): Flow<List<Sneaker>>

    fun getSneakersById(id: String): Flow<Sneaker?>
}