package com.example.punktozercy

import com.example.punktozercy.model.Product

class SelectedProducts {



    companion object{

        var productList : MutableList<Product> = mutableListOf(Product(1,"test",1.0,"test","test",1,"test"))
        var test: String = "NONE"
        var firstAdd = true
    }

    fun setTest(str: String){
        test = str;
    }

    fun getTest(): String{
        return test;
    }

    fun clearProductList(){
        productList.clear()
    }

    fun addProduct(product: Product){
        if(firstAdd) {
            productList.clear()
            firstAdd = false
        }

        productList.add(product)
    }

    fun removeProduct(position: Int){
        productList.removeAt(position)
    }

    fun getProductList(): List<Product>{
        return productList
    }
}