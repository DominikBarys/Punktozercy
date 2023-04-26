package com.example.punktozercy.model

import androidx.room.*


/**
 * class that represents shopping history entity. It have 2 foreign keys
 * @property userid from User entity and productId from Product entity
 * @property id shopping history entity
 * @property date product buy date
 * @property userid id of the user
 * @property productId id of the product
 * @property isBoughtByMoney when the value is equal 1 it means that
 * user bought product by money, if value is equal 0 it means
 * that user bought product using points
 */
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

/**
 * data class that represents the return type of the query from shopping history
 * @property userShoppingHistory shopping history object
 * @property product embedded object needed for getting product data
 */

data class UserShoppingHistoryWithProduct(
    @Embedded
    val userShoppingHistory: ShoppingHistory,
    @Embedded
    val product: Product
)









