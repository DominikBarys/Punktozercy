package com.example.punktozercy.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.punktozercy.model.ShopDatabase
import com.example.punktozercy.model.User
import com.example.punktozercy.repository.ShopRepository
import kotlinx.coroutines.*

class ShopViewModel(application: Application) : AndroidViewModel(application) {
   private val loadAllUsers : LiveData<List<User>>
   private val repository: ShopRepository

   init {
       val userDao = ShopDatabase.getDatabase(application).userDao()
       val productDao = ShopDatabase.getDatabase(application).productDao()
       val shoppingHistoryDao = ShopDatabase.getDatabase(application).shoppingHistoryDao()
       repository = ShopRepository(userDao, productDao, shoppingHistoryDao)
       loadAllUsers = repository.loadAllUsers
   }

    fun addUser(user:User){
        viewModelScope.launch (Dispatchers.IO){
            repository.addUser(user)
        }
    }
   suspend fun updateUserData(userid:Long,username:String,Email:String,Password:String?,phone:String?,Address:String?){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateUserData(userid, username, Email, Password, phone, Address)
        }
    }

    suspend fun updateUserPoints(userid: Long,points:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUserPoints(userid,points)
        }
    }

    suspend fun updateReceiverUserPoints(email: String,points: Int){
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