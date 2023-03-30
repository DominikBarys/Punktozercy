package com.example.punktozercy.model

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertProduct(product:Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products:List<Product>)

    @Update
    fun updateProduct(product:Product)

    @Update
    fun updateProducts(products: Product)

    @Delete
    fun deleteProducts(vararg products: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM products")
    fun loadAllProducts(): LiveData<List<Product>>
}