package com.example.punktozercy.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.punktozercy.model.ShopDatabase
import com.example.punktozercy.model.User
import com.example.punktozercy.repository.ShopRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
}