package com.example.punktozercy.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user:User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users:User)

    @Update
    fun updateUser(user:User)

    @Update
    fun updateUsers(users:User)

    @Delete
    fun deleteUsers(vararg users: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    fun loadAllUsers(): LiveData<List<User>>

    //TODO
    @Query("Select email FROM user WHERE :email = email ")
    fun findUserWithEmail(email:String):List<String>
}