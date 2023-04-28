package com.example.punktozercy

import androidx.lifecycle.MutableLiveData
import com.example.punktozercy.model.Product

/**
 * class to store important information that is mainly used by adapters that do not have access to
 * ViewModels
 * @property _textMoneyPrice
 * @property textMoneyPrice
 * @property _textPointsPrice
 * @property textPointsPrice
 * @property _basketText
 * @property basketText
 * @property firstProduct
 * @property productList
 * @property firstAdd
 * @property _amountOfProductsInBasket
 * @property amountOfProductsInBasket
 * @property clearProductList
 * @property addProduct
 * @property removeProduct
 * @property getProductList
 */
class SharedData {
    companion object {
        /**
         * a variable storing a value in money calculated on the basis of products in the basket
         */
        var _textMoneyPrice = MutableLiveData<Double>().apply {
            value = 0.0
        }

        /**
         * a variable that stores value from _textMoneyPrice
         */
        var textMoneyPrice: MutableLiveData<Double> = _textMoneyPrice

        /**
         * a variable storing a value in points calculated on the basis of products in the basket
         */
        var _textPointsPrice = MutableLiveData<Int>().apply {
            value = 0
        }

        /**
         * a variable that stores value from _textPointsPrice
         */
        var textPointsPrice: MutableLiveData<Int> = _textPointsPrice

        /**
         * text informing the user about an empty basket
         */
        var _basketText = MutableLiveData<String>().apply {
            value = "YOU HAVE EMPTY BASKET"
        }

        /**
         * a variable that stores value from _basketText
         */
        var basketText: MutableLiveData<String> = _basketText

        /**
         * a variables that stores information whether a given product that the user adds to the
         * basket is the first one. The information is necessary for the correct operation of the
         * algorithm
         */
        var firstProduct = true
        var firstAdd = true

        /**
         * list of products in the basket. The variable must be pre-initialized with a test value
         * because of the MutableList<> property
         */
        var productList: MutableList<Product> =
            mutableListOf(Product(1, "test", 1.0, "test", "test", 1, "test"))

        /**
         * variable that stores amount of products in basket
         */
        var _amountOfProductsInBasket = MutableLiveData<Int>().apply {
            value = 0
        }

        /**
         * a variable that stores value from _amountOfProductsInBasket
         */
        var amountOfProductsInBasket: MutableLiveData<Int> = _amountOfProductsInBasket
    }

    /**
     * function that clears the list of products
     */
    fun clearProductList(){
        productList.clear()
    }

    /**
     * the function adds one product to the list, it also takes into account whether the product
     * was added for the first time and whether the given product will be the first in the list
     */
    fun addProduct(product: Product){
        firstProduct = false
        if(firstAdd) {
            productList.clear()
            firstAdd = false
        }

        basketText.value = ""
        productList.add(product)
    }

    /**
     * function that removes the product in the indicated position from the list
     * @param position position of product in list
     */
    fun removeProduct(position: Int){
        productList.removeAt(position)
        if(productList.size == 0){
            basketText.value = "YOU HAVE EMPTY BASKET"
        }
    }

    /**
     * a function that returns a list of products
     * @return list of products
     */
    fun getProductList(): List<Product>{
        return productList
    }

}