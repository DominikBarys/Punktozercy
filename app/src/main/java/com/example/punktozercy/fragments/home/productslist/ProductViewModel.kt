package com.example.punktozercy.fragments.home.productslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel: ViewModel() {

    companion object{
        var selectedCategory: String = "None"
    }

    fun setCategory(cat: String){
        selectedCategory = cat
    }

    fun getCategory(): String{
        return selectedCategory
    }

//    var _amountOfProductsInBas
//   ket = MutableLiveData<Int>().apply{
//        value = 5
//    }
//    var amountOfProductsInBasket : MutableLiveData<Int> = _amountOfProductsInBasket
}