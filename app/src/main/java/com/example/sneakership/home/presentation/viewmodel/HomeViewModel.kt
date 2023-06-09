package com.example.sneakership.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakership.home.domain.model.Sneaker
import com.example.sneakership.home.domain.repository.ISneakerRepository
import com.example.sneakership.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: ISneakerRepository
) : ViewModel(), IHomeViewModelContract {


    /*Get Sneakers*/
    private val sneakers = MutableLiveData<Resource<List<Sneaker>>>()

    override fun getSneakers() {
        viewModelScope.launch {
            repo.getSneakers()
                .onStart {
                    sneakers.postValue(Resource.Loading)
                }
                .catch { e ->
                    sneakers.postValue(Resource.Failure(e.message))
                }
                .collect { list ->
                    sneakers.postValue(Resource.Success(list))
                }
        }
    }

    override fun observeGetSneakers(): LiveData<Resource<List<Sneaker>>> =
        sneakers


    /*Get Sneaker By Id*/
    private val sneakerById = MutableLiveData<Resource<Sneaker>>()

    override fun getSneakerById(id: String) {
        viewModelScope.launch {
            repo.getSneakersById(id)
                .onStart {
                    sneakerById.postValue(Resource.Loading)
                }
                .catch { e ->
                    sneakerById.postValue(Resource.Failure(e.message))
                }
                .collect { sneaker ->
                    if (sneaker != null) {
                        sneakerById.postValue(Resource.Success(sneaker))
                    } else {
                        sneakerById.postValue(Resource.Failure("Shoe not found"))
                    }
                }
        }
    }

    override fun observeGetSneakerById(): LiveData<Resource<Sneaker>> =
        sneakerById

    /*search Query*/
    private val searchQuery = MutableLiveData<String>()
    fun searchQuery(query: String) : LiveData<String> {
        searchQuery.value = query
        return searchQuery
    }
}