package com.example.punktozercy.fragments.history

import androidx.lifecycle.ViewModel
import com.example.punktozercy.model.UserShoppingHistoryWithProduct

/**
 * class responsible for storing and managing the data associated with the user history view
 * @property userShoppingHistory
 * @property getUserShoppingHistory
 * @property setUserShoppingHistory
 */
class HistoryViewModel:ViewModel() {

    /**
     * list with products and dates
     */
    companion object{
        lateinit var userShoppingHistory:List<UserShoppingHistoryWithProduct>
    }


    /**
     * function responsible for getting user shopping history list
     * @return list with products and dates
     */
    fun getUserShoppingHistory():List<UserShoppingHistoryWithProduct>{
        return userShoppingHistory
    }

    /**
     * function responsible for setting user shopping history list
     * @param list list with products and dates
     */
    fun setUserShoppingHistory(list:List<UserShoppingHistoryWithProduct>){
        userShoppingHistory = list
    }
}