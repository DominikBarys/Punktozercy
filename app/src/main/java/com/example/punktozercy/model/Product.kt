package com.example.punktozercy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val pointsPrice:Int?

)


