package com.example.punktozercy.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.punktozercy.R
import com.example.punktozercy.fragments.home.adapters.Offer

class HomeViewModel : ViewModel() {

    val offerArray = arrayOf(   Triple(R.drawable.offer_burger, "Hot burgers in your neighbourhood!", "Food"),
                                Triple(R.drawable.offer_chips, "CHIPS, YE YE", "Food"),
                                Triple(R.drawable.offer_choclate, "Sweet and delicious", "Food"),
                                Triple(R.drawable.offer_drink, "Don't drink while driving", "Drink"),
                                Triple(R.drawable.offer_ketchup, "Raw sauce", "Other"),
                                Triple(R.drawable.offer_drinks, "Maaany drinks", "Drink")
    )


    var _selectedCategory = MutableLiveData<String>().apply{
        value = "None"
    }

    var selectedCategory: MutableLiveData<String> = _selectedCategory

    val HELLO_USER = "Hello, "

    val POINTS_STRING = "You have "
    val POINTS_STRING2 = " points!"


}