package com.example.sneakership.home.data.remote

import com.example.sneakership.home.domain.model.SneakerRecord
import com.example.sneakership.utils.network.ApiEndPoints
import retrofit2.http.GET

interface ISneakerApi {

    @GET(ApiEndPoints.GET_SNEAKERS)
    suspend fun getSneakers(): SneakerRecord
}