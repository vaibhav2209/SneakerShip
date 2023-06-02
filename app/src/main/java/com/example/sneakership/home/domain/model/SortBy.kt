package com.example.sneakership.home.domain.model

sealed class SortBy {
    object Relevant : SortBy()
    object PriceHighest : SortBy()
    object PriceLowest : SortBy()
}
