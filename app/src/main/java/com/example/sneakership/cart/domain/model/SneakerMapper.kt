package com.example.sneakership.cart.domain

import com.example.sneakership.cart.domain.model.SneakerEntity
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.home.domain.model.SneakerImage

fun SneakerEntity.toSneaker() =
    Sneaker(
        id = id,
        brand = brand,
        colorway = colorway,
        gender = gender,
        sneakerImage = SneakerImage(
            imageUrl = imageUrl,
            smallImageUrl = smallImageUrl,
            thumbUrl = thumbnailUrl
        ),
        name = name,
        releaseDate = releaseDate,
        retailPrice = retailPrice,
        shoe = shoe,
        styleId = styleId,
        title = title,
        year = year
    )

fun Sneaker.toSneakerEntity() =
    SneakerEntity(
        id = id,
        brand = brand,
        colorway = colorway,
        gender = gender,
        imageUrl = sneakerImage.imageUrl,
        smallImageUrl = sneakerImage.smallImageUrl,
        thumbnailUrl = sneakerImage.thumbUrl,
        name = name,
        releaseDate = releaseDate,
        retailPrice = retailPrice,
        shoe = shoe,
        styleId = styleId,
        title = title,
        year = year
    )