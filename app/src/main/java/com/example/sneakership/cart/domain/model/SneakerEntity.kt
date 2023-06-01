package com.example.sneakership.cart.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sneakership.home.domain.model.SneakerImage
import com.google.gson.annotations.SerializedName

@Entity
data class SneakerEntity(
    @PrimaryKey
    val id: String,
    val brand: String,
    val colorway: String,
    val gender: String,
    val smallImageUrl : String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val name: String,
    val releaseDate: String,
    val retailPrice: Int,
    val shoe: String,
    val styleId: String,
    val title: String,
    val year: Int
)