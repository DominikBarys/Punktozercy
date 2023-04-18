package com.example.punktozercy.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
@Dao
interface ShoppingHistoryDao {


//    @Query("SELECT * FROM  shopping-histories")
//    fun getUserShoppingHistory(): List<ShoppingHistory>

    @Insert
    fun addUserShoppingHistory(shoppingHistory: ShoppingHistory)


}