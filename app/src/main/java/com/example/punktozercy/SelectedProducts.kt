package com.example.punktozercy

import androidx.lifecycle.MutableLiveData
import com.example.punktozercy.model.Product

class SelectedProducts {
    companion object {

        var _textMoneyPrice = MutableLiveData<Double>().apply {
            value = 0.0
        }

        var textMoneyPrice: MutableLiveData<Double> = _textMoneyPrice

        var _textPointsPrice = MutableLiveData<Double>().apply {
            value = 0.0
        }

        var textPointsPrice: MutableLiveData<Double> = _textPointsPrice

        var _basketText = MutableLiveData<String>().apply {
            value = "YOU HAVE EMPTY BASKET"
        }

        var basketText: MutableLiveData<String> = _basketText

        var firstProduct = true;

        var productList: MutableList<Product> =
            mutableListOf(Product(1, "test", 1.0, "test", "test", 1, "test"))

        var firstAdd = true


        var _amountOfProductsInBasket = MutableLiveData<Int>().apply {
            value = 0
        }
        var amountOfProductsInBasket: MutableLiveData<Int> = _amountOfProductsInBasket
    }

    fun clearProductList(){
        productList.clear()
    }

    fun addProduct(product: Product){
        firstProduct = false
        if(firstAdd) {
            productList.clear()
            firstAdd = false
        }

        basketText.value = ""
        productList.add(product)
    }

    fun removeProduct(position: Int){
        productList.removeAt(position)
        if(productList.size == 0){
            basketText.value = "YOU HAVE EMPTY BASKET"
        }
    }

    fun getProductList(): List<Product>{
        return productList
    }

}