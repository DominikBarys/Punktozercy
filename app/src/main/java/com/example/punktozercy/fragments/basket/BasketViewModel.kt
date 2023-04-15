package com.example.punktozercy.fragments.basket

import androidx.lifecycle.ViewModel
import com.example.punktozercy.model.Product

class BasketViewModel : ViewModel(){

    companion object{
        lateinit var productList : List<Product>
        var test: String = "NONE"
    }

    fun setTest(str: String){
        test = str;
    }

    fun getTest(): String{
        return test;
    }


}