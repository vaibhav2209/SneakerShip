package com.example.sneakership.application

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sneakership.cart.data.local.ISneakerDao
import com.example.sneakership.cart.domain.model.SneakerEntity

@Database(
    entities = [SneakerEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sneakerDao() : ISneakerDao

    companion object {
        const val ROOM_DATABASE = "SNEAKER_ROOM_DATABASE"
    }
}