package com.example.sneakership.utils

sealed class Resource<out R> {

    data class Success<out R>(val result: R): Resource<R>()

    data class Failure(val message: String?): Resource<Nothing>()

    object Loading: Resource<Nothing>()

    object NoResult: Resource<Nothing>()
}