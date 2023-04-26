package com.example.punktozercy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 *class that represents user Entity
 * @property userId id of the user
 * @property userName username of the user
 * @property password user password
 * @property phoneNumber user phone number
 * @property address user address
 * @property email user email
 * @property points number of user points
 * @property googleToken google token needed for signing by google
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId:Long,

    @ColumnInfo(name = "username")
    val userName:String,
    @ColumnInfo(name = "password")
    val password: String?,
    @ColumnInfo(name = "phone_number")
    val phoneNumber:String?,
    @ColumnInfo(name = "address")
    val address:String?,
    @ColumnInfo(name = "email")
    val email:String,
    @ColumnInfo(name = "points")
    val points:Int,
    @ColumnInfo(name = "google_token")
    val googleToken:String?


)
