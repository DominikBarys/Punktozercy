package com.example.punktozercy.model

import androidx.room.*

/**
 * Shopping history Dao interface responsible for interacting with the database
 * @property getUserShoppingHistory
 */
@Dao
interface ShoppingHistoryDao {

    /**
     * function used for getting user shopping history from database
     * @param userId id of the user
     * @return list of user shopping history objects
     */
   @Query("SELECT shopping_histories.*,products.* FROM shopping_histories INNER JOIN products ON shopping_histories.productid = products.productId  WHERE shopping_histories.userid = :userId")
    fun getUserShoppingHistory(userId:Long): List<UserShoppingHistoryWithProduct>





    /**
     * function used for inserting shopping history to database
     * @param shoppingHistory shopping history object
     */
    @Insert
    fun addUserShoppingHistory(shoppingHistory: ShoppingHistory)


}