package com.example.punktozercy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *class that represents product Entity
 * @property productId id of the product
 * @property name name of the product
 * @property price price of the product
 * @property description description of the product
 * @property type type of product(drink, meal, etc.)
 * @property pointsPrice price of the product in points
 * @property imagePath path to the image of the product
 */
@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId:Long,
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name = "price")
    val price:Double,
    @ColumnInfo(name = "description")
    val description:String,
    @ColumnInfo(name = "type")
    val type:String,
    @ColumnInfo(name = "points_price")
    val pointsPrice:Int?,
    @ColumnInfo(name = "image_path")
    val imagePath: String?

)


