package com.example.punktozercy.model

import androidx.room.*
import com.example.punktozercy.model.Product
import com.example.punktozercy.model.User


@Entity(tableName = "shopping-histories")
data class ShoppingHistory(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    @ColumnInfo(name = "userId")
    val userId:Long,
    @ColumnInfo(name = "productId")
    val productId:Long,
    @ColumnInfo(name = "date")
    val date:String

)


@Entity(primaryKeys = ["userId", "productId"])
data class UserProductCrossRef(
    val userId: Long,
    val productId: Long
)


data class UserWithProducts(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "productId",
        associateBy = Junction(UserProductCrossRef::class)
    )
    val products: List<Product>
)



