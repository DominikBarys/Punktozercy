package com.example.punktozercy.fragments.history

import androidx.lifecycle.ViewModel
import com.example.punktozercy.model.Product
import com.example.punktozercy.model.ShoppingHistory
import com.example.punktozercy.model.User

class HistoryViewModel:ViewModel() {

    companion object{
        lateinit var userShoppingHistory:Map<String,List<Product>>
    }

    fun getUserShoppingHistory():Map<String,List<Product>>{
        return userShoppingHistory
    }

    fun setUserShoppingHistory(list:Map<String,List<Product>>){
        userShoppingHistory = list
    }
}