package com.example.punktozercy.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId:Long,

    @ColumnInfo(name = "user_name")
    val userName:String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "phone_number")
    val phoneNumber:String,
    @ColumnInfo(name = "address")
    val address:String?,
    @ColumnInfo(name = "email")
    val email:String,
    @ColumnInfo(name = "points")
    val points:Int


):Parcelable
