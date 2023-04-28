package com.example.punktozercy.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.punktozercy.R

/**
 * class responsible for storing and managing the data associated with the home view
 * @property offerArray
 * @property _selectedCategory
 * @property selectedCategory
 * @property HELLO_USER
 * @property POINTS_STRING_PREFIX
 * @property POINTS_STRING_SUFIX
 */
class HomeViewModel : ViewModel() {

    /**
     * array of triples that each of them represents one offer
     */
    val offerArray = arrayOf(
        Triple(R.drawable.offer_readymeals, "ready meals", "ready meals"),
        Triple(R.drawable.offer_beverages, "beverages", "beverages"),
        Triple(R.drawable.offer_dairy, "dairy", "dairy"),
        Triple(R.drawable.offer_drink, "drink", "drink"),
        Triple(R.drawable.offer_spices, "spices", "spices"),
        Triple(R.drawable.offer_fruits, "fruits", "fruits"),
        Triple(R.drawable.offer_bakery, "bakery", "bakery"),
        Triple(R.drawable.offer_meat, "meat", "meat"),
        Triple(R.drawable.offer_vegetables, "vegetables", "vegetables"),
        Triple(R.drawable.offer_sweets, "sweets", "sweets"),
        Triple(R.drawable.offer_fizzydrink, "fizzy drink", "fizzy drink")
    )


    /**
     * variable storing information about the offer selected by the user
     */
    var _selectedCategory = MutableLiveData<String>().apply{
        value = "None"
    }

    /**
     * variable that stores value from _selectedCategory
     */
    var selectedCategory: MutableLiveData<String> = _selectedCategory

    /**
     * a set of constant variables used to display user information
     */
    val HELLO_USER = "Hello, "
    val POINTS_STRING_PREFIX = "You have "
    val POINTS_STRING_SUFIX = " points!"

}