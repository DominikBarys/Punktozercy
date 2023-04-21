package com.example.punktozercy.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.punktozercy.R
import com.example.punktozercy.fragments.home.adapters.Offer

class HomeViewModel : ViewModel() {

    val offerArray = arrayOf(   Triple(R.drawable.offer_readymeals, "ready meals", "Food"),
                                Triple(R.drawable.offer_drink, "drink", "Drink"),
                                Triple(R.drawable.offer_spices, "spices", "Other"),
                                Triple(R.drawable.offer_fruits, "fruits", "Food"),
                                Triple(R.drawable.offer_bakery, "bakery", "Food"),
                                Triple(R.drawable.offer_dairy, "dairy", "Food"),
                                Triple(R.drawable.offer_meat, "meat", "Food"),
                                Triple(R.drawable.offer_vegetables, "vegetables", "Food"),
                                Triple(R.drawable.offer_beverages, "beverages", "Drink"),
                                Triple(R.drawable.offer_sweets, "sweets", "Other"),
                                Triple(R.drawable.offer_fizzydrink, "fizzy drink", "Drink")
    )


    var _selectedCategory = MutableLiveData<String>().apply{
        value = "None"
    }

    var selectedCategory: MutableLiveData<String> = _selectedCategory

    val HELLO_USER = "Hello, "

    val POINTS_STRING = "You have "
    val POINTS_STRING2 = " points!"


}