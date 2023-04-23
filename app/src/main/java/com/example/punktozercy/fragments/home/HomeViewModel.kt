package com.example.punktozercy.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.punktozercy.R


class HomeViewModel : ViewModel() {

    val offerArray = arrayOf(   Triple(R.drawable.offer_readymeals, "ready meals", "ready meals"),
        Triple(R.drawable.offer_drink, "drink", "drink"),
        Triple(R.drawable.offer_spices, "spices", "spices"),
        Triple(R.drawable.offer_fruits, "fruits", "fruits"),
        Triple(R.drawable.offer_bakery, "bakery", "bakery"),
        Triple(R.drawable.offer_dairy, "dairy", "dairy"),
        Triple(R.drawable.offer_meat, "meat", "meat"),
        Triple(R.drawable.offer_vegetables, "vegetables", "vegetables"),
        Triple(R.drawable.offer_beverages, "beverages", "beverages"),
        Triple(R.drawable.offer_sweets, "sweets", "sweets"),
        Triple(R.drawable.offer_fizzydrink, "fizzy drink", "fizzy drink")
    )


    var _selectedCategory = MutableLiveData<String>().apply{
        value = "None"
    }

    var selectedCategory: MutableLiveData<String> = _selectedCategory

    val HELLO_USER = "Hello, "

    val POINTS_STRING = "You have "
    val POINTS_STRING2 = " points!"


}