package com.example.punktozercy.fragments.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.punktozercy.model.Product

class BasketViewModel : ViewModel(){



    companion object{
        lateinit var productList : List<Product>
        var test: String = "NONE"

        private var _buyUsingMoney = MutableLiveData<Boolean>().apply {
            value = true
        }

        var buyUsingMoney: MutableLiveData<Boolean> = _buyUsingMoney
    }




}