package com.example.sneakership.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.home.domain.model.SortBy
import com.example.sneakership.utils.Resource

interface IHomeViewModelContract {
    fun observeGetSneakers(): LiveData<Resource<List<Sneaker>>>

    fun observeGetSneakerById(): LiveData<Resource<Sneaker>>
    fun getSneakerById(id: String)
    fun getSneaker(sortBy: SortBy = SortBy.Relevant, q: String)
}