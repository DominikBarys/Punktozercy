package com.example.punktozercy.model

import androidx.room.*

interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertProduct(product:Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(vararg products:Product)

    @Update
    fun updateProduct(product:Product)

    @Update
    fun updateProducts(products: Product)

    @Delete
    fun deleteProducts(vararg products: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM products")
    fun loadAllProducts(): Array<Product>
}