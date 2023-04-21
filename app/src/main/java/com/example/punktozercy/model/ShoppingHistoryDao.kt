package com.example.punktozercy.model

import androidx.room.*

@Dao
interface ShoppingHistoryDao {


//    @MapInfo(keyColumn = "date")
   @Query("SELECT shopping_histories.*,products.* FROM shopping_histories INNER JOIN products ON shopping_histories.productid = products.productId  WHERE shopping_histories.userid = :userId")
    fun getUserShoppingHistory(userId:Long): List<UserShoppingHistoryWithProduct>






    @Insert
    fun addUserShoppingHistory(shoppingHistory: ShoppingHistory)


}