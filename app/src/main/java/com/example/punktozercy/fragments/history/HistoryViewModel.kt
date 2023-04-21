package com.example.punktozercy.fragments.history

import androidx.lifecycle.ViewModel
import com.example.punktozercy.model.Product
import com.example.punktozercy.model.ShoppingHistory
import com.example.punktozercy.model.User
import com.example.punktozercy.model.UserShoppingHistoryWithProduct

class HistoryViewModel:ViewModel() {

    companion object{
        lateinit var userShoppingHistory:List<UserShoppingHistoryWithProduct>
    }

    fun getUserShoppingHistory():List<UserShoppingHistoryWithProduct>{
        return userShoppingHistory
    }

    fun setUserShoppingHistory(list:List<UserShoppingHistoryWithProduct>){
        userShoppingHistory = list
    }
}