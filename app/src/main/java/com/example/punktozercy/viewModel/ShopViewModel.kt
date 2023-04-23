package com.example.punktozercy.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.punktozercy.model.*
import com.example.punktozercy.repository.ShopRepository
import kotlinx.coroutines.*

class ShopViewModel(application: Application) : AndroidViewModel(application) {
   private val repository: ShopRepository

   init {
       val userDao = ShopDatabase.getDatabase(application).userDao()
       val productDao = ShopDatabase.getDatabase(application).productDao()
       val shoppingHistoryDao = ShopDatabase.getDatabase(application).shoppingHistoryDao()
       repository = ShopRepository(userDao, productDao, shoppingHistoryDao)


   }

    //-------------------------------------USER WITH PRODUCTS QUERY---------------------------------------------------

     fun addUserShoppingHistory(shoppingHistory: ShoppingHistory){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserShoppingHistory(shoppingHistory)
        }
    }

    suspend fun getUserShoppingHistory(userId:Long): List<UserShoppingHistoryWithProduct>{
        return withContext(Dispatchers.IO) {
            return@withContext repository.getUserShoppingHistory(userId)
        }
    }

    //--------------------------------------PRODUCT FUNCTIONS---------------------------------------------------------


    suspend fun getProductsByCategory(category: String):List<Product>{
        return withContext(Dispatchers.IO) {
            return@withContext repository.getProductsByCategory(category)
        }
    }

    //---------------------------------------USER FUNCTIONS-----------------------------------------------------------
    fun addUser(user:User){
        viewModelScope.launch (Dispatchers.IO){
            repository.addUser(user)
        }
    }
    fun updateUserData(userid:Long,username:String,Email:String,Password:String?,phone:String?,Address:String?){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateUserData(userid, username, Email, Password, phone, Address)
        }
    }

     fun updateGoogleUserData(userid:Long,Username:String,Email:String,Password:String?,phone:String?,Address:String?,token:String?){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateGoogleUserData(userid, Username, Email, Password, phone, Address, token)
        }
    }

     fun updateUserPoints(userid: Long,points:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUserPoints(userid,points)
        }
    }

     fun updateReceiverUserPoints(email: String,points: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateReceiverUserPoints(email,points)
        }
    }

     @OptIn(ExperimentalCoroutinesApi::class)
     suspend fun isUserLoginExists(_email:String, _password:String):List<User>{
         return withContext(Dispatchers.IO) {
             return@withContext repository.isUserLoginExists(_email, _password)
         }
     }
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun findUserByGoogleToken(token:String):List<User>{
        return withContext(Dispatchers.IO) {
            return@withContext repository.findUserByGoogleToken(token)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun isUserNameExists(username:String):Boolean{
        return withContext(Dispatchers.IO) {
            return@withContext repository.isUserNameExists(username)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun isUserEmailExists(_email:String):Boolean{
        return withContext(Dispatchers.IO) {
            return@withContext repository.isUserEmailExists(_email)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getUserById(userid: Long):User{
        return withContext(Dispatchers.IO) {
            return@withContext repository.getUserById(userid)
        }
    }

     fun updateUsers(user:User){
         viewModelScope.launch (Dispatchers.IO){
             repository.updateUsers(user)
         }
    }





}