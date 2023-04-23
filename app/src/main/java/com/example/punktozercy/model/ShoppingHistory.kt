package com.example.punktozercy.model

import androidx.room.*





@Entity(tableName = "shopping_histories",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userid"]),
    ForeignKey(entity = Product::class, parentColumns = ["productId"], childColumns = ["productid"])
    ]
)
data class ShoppingHistory(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    @ColumnInfo(name = "date")
    val date:String,
    @ColumnInfo(name = "userid")
    val userid:Long,
    @ColumnInfo(name = "productid")
    val productId:Long,
    @ColumnInfo(name = "buy_type")
    val isBoughtByMoney: Boolean

)

data class UserShoppingHistoryWithProduct(
    @Embedded
    val userShoppingHistory: ShoppingHistory,
    @Embedded
    val product: Product
)









