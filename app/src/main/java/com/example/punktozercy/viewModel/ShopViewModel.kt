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

    //TODO Do przerobienia
    fun findUserByEmail(email:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.findUserByEmail(email)
        }
    }

     @OptIn(ExperimentalCoroutinesApi::class)
     suspend fun isUserLoginExists(_email:String, _password:String):Boolean{
         return withContext(Dispatchers.IO) {
             return@withContext repository.isUserLoginExists(_email, _password)
         }
     }
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun findUserByGoogleToken(token:String):User{
        return withContext(Dispatchers.IO) {
            return@withContext repository.findUserByGoogleToken(token)
        }
    }




}