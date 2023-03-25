package com.example.punktozercy.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
@Dao
interface ShoppingHistoryDao {

    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersWithProducts(): List<UserWithProducts>
}