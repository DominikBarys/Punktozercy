package com.example.punktozercy.model

import androidx.room.*

@Dao
interface ShoppingHistoryDao {


    @MapInfo(keyColumn = "date")
   @Query("SELECT shopping_histories.date,products.* FROM shopping_histories INNER JOIN products ON shopping_histories.productid = products.productId  WHERE shopping_histories.userid = :userId")
    fun getUserShoppingHistory(userId:Long): Map<String,List<Product>>






    @Insert
    fun addUserShoppingHistory(shoppingHistory: ShoppingHistory)


}