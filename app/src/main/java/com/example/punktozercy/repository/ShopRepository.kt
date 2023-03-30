package com.example.punktozercy.repository

import androidx.lifecycle.LiveData
import com.example.punktozercy.model.*

class ShopRepository(private val userDao: UserDao,private val productDao: ProductDao,private val shoppingHistoryDao: ShoppingHistoryDao) {

    val loadAllUsers : LiveData<List<User>> = userDao.loadAllUsers();
    val loadAllProducts: LiveData<List<Product>> = productDao.loadAllProducts();
    //TODO
    //val loadUsersWithProducts
    suspend fun addUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun findUserByEmail(email:String){
        userDao.findUserWithEmail(email)
    }
     suspend fun isUserLoginExists(_email:String,_password:String):Boolean{
       return  userDao.isUserLoginExists(_email,_password)
    }

    suspend fun findUserByGoogleToken(token:String):User{
        return userDao.findUserByGoogleToken(token)
    }
}