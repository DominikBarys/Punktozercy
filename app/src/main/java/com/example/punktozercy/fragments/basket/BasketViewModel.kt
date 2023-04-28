package com.example.punktozercy.fragments.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * class responsible for storing and managing the data associated with the user basket view
 * @property _buyUsingMoney
 * @property buyUsingMoney
 */
class BasketViewModel : ViewModel(){

    companion object{
        /**
         * a variable that changes frequently as the program runs. Informs about the user's payment
         * method
         */
        private var _buyUsingMoney = MutableLiveData<Boolean>().apply {
            value = true
        }

        /**
         * variable that stores boolean value from _buyUsingMoney
         */
        var buyUsingMoney: MutableLiveData<Boolean> = _buyUsingMoney
    }

}