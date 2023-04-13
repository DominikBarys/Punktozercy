package com.example.punktozercy.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.punktozercy.R
import com.example.punktozercy.fragments.home.adapters.Offer

class HomeViewModel : ViewModel() {

    val offerArray = arrayOf(   Triple(R.drawable.offer_burger, "dairy", "dairy"),
                                Triple(R.drawable.offer_chips, "meat", "meat"),
                                Triple(R.drawable.offer_choclate, "vegetables", "vegetables"),
                                Triple(R.drawable.offer_drink, "bakery", "bakery"),
                                Triple(R.drawable.offer_ketchup, "beverages", "beverages"),
                                Triple(R.drawable.offer_drinks, "sweets", "sweets"),
                                Triple(R.drawable.offer_drinks, "ready meals", "ready meals"),
                                Triple(R.drawable.offer_drinks, "fizzy drink", "fizzy drink"),
                                Triple(R.drawable.offer_drinks, "drink", "drink"),
                                Triple(R.drawable.offer_drinks, "fruits", "fruits"),
                                Triple(R.drawable.offer_drinks, "spices", "spices")
    )


    var _selectedCategory = MutableLiveData<String>().apply{
        value = "None"
    }

    var selectedCategory: MutableLiveData<String> = _selectedCategory

    val HELLO_USER = "Hello, "

    val POINTS_STRING = "You have "
    val POINTS_STRING2 = " points!"


}