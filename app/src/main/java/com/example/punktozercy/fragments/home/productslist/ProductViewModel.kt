package com.example.punktozercy.fragments.home.productslist

import androidx.lifecycle.ViewModel
import com.example.punktozercy.fragments.history.HistoryViewModel.Companion.userShoppingHistory
import com.example.punktozercy.model.Product
/**
 * class responsible for storing and managing the data associated with the product  view
 * @property selectedCategory
 * @property productList
 * @property setProductList
 * @property getProductList
 * @property setCategory
 * @property getCategory
 */
class ProductViewModel: ViewModel() {

    companion object{
        // selected category
        var selectedCategory: String = "None"
        // product list by category
        lateinit var productList : List<Product>
    }

    /**
     * function that is responsible for setting product list
     * @param products list of products
     */
    fun setProductList(products:List<Product>){
        productList = products
    }

    /**
     * function that is responsible for getting product list
     * @return product list
     */
    fun getProductList():List<Product>{
        return productList
    }

    /**
     * function that sets the category
     * @param cat category of the products
     */
    fun setCategory(cat: String){
        selectedCategory = cat
    }

    /**
     * function that gets category
     * @return category of the products
     */
    fun getCategory(): String{
        return selectedCategory
    }

}