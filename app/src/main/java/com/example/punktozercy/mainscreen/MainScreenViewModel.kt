package com.example.punktozercy.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainScreenViewModel : ViewModel(){

    var _amountOfProductsInBasket = MutableLiveData<Int>().apply{
        value = 5
    }
    var amountOfProductsInBasket : MutableLiveData<Int> = _amountOfProductsInBasket


}