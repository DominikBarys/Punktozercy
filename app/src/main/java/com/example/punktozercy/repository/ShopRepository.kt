package com.example.punktozercy.repository

import androidx.lifecycle.LiveData
import com.example.punktozercy.model.ProductDao
import com.example.punktozercy.model.ShoppingHistoryDao
import com.example.punktozercy.model.User
import com.example.punktozercy.model.UserDao

class ShopRepository(private val userDao: UserDao,private val productDao: ProductDao,private val shoppingHistoryDao: ShoppingHistoryDao) {

    val loadAllUsers : LiveData<List<User>> = userDao.loadAllUsers();

    suspend fun addUser(user: User){
        userDao.insertUser(user)
    }
}