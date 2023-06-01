package com.example.sneakership.home.domain.model

import com.example.sneakership.home.domain.model.SneakerImage
import com.google.gson.annotations.SerializedName

data class Sneaker(
    val brand: String,
    val colorway: String,
    val gender: String,
    val id: String,
    @SerializedName("media")
    val sneakerImage: SneakerImage,
    val name: String,
    val releaseDate: String,
    val retailPrice: Int,
    val shoe: String,
    val styleId: String,
    val title: String,
    val year: Int
)