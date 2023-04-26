package com.example.punktozercy.model

import androidx.lifecycle.LiveData
import androidx.room.*


/**
 * ProductDao interface responsible for interacting with the database
 * @property insertProduct
 * @property insertProducts
 * @property getProductsByCategory
 * @property updateProduct
 * @property deleteProducts
 * @property deleteProduct
 * @property loadAllProducts
 */
@Dao
interface ProductDao {

    /**
     * function responsible for inserting a product to database
     * @param product Product object
     */

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertProduct(product:Product)
    /**
     * function responsible for inserting a list of products to database
     * @param products list of Product objects
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products:List<Product>)


    /**
     * function responsible for getting a list of products by category type from database
     * @param category name of the category
     * @return list of Product objects
     */
    @Query("SELECT * FROM products WHERE type = :category")
    fun getProductsByCategory(category:String):List<Product>
    /**
     * function responsible for updating a product in database
     * @param product Product object
     */
    @Update
    fun updateProduct(product:Product)


    /**
     * function responsible for deleting products from database
     * @param products Product objects
     */
    @Delete
    fun deleteProducts(vararg products: Product)
    /**
     * function responsible for deleting product from database
     * @param product Product object
     */
    @Delete
    fun deleteProduct(product: Product)
    /**
     * function responsible for getting all of the products from database
     * @return list of Product objects
     */
    @Query("SELECT * FROM products")
    fun loadAllProducts(): LiveData<List<Product>>
}