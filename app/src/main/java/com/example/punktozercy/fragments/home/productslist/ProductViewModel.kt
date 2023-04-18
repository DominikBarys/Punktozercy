package com.example.punktozercy.fragments.home.productslist

import androidx.lifecycle.ViewModel
import com.example.punktozercy.model.Product

class ProductViewModel: ViewModel() {

    companion object{
        var selectedCategory: String = "None"
        lateinit var productList : List<Product>
    }
    fun setProductList(products:List<Product>){
        productList = products
    }
    fun getProductList():List<Product>{
        return productList
    }

    fun setCategory(cat: String){
        selectedCategory = cat
    }

    fun getCategory(): String{
        return selectedCategory
    }

}