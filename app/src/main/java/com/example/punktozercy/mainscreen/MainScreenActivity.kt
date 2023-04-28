package com.example.punktozercy.mainscreen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.punktozercy.R
import com.example.punktozercy.SharedData
import com.example.punktozercy.databinding.ActivityMain2Binding
import com.example.punktozercy.fragments.settings.SettingsViewModel
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel

/**
 * class responsible for a single screen that the user can see and interact with. It is an parent
 * of the settings, edit profile, send points, product list , home, basket fragments
 * @property binding
 * @property userViewModel
 * @property mShopViewModel
 * @property sharedPreferences
 * @property settingsViewModel
 * @property onCreate
 */
class MainScreenActivity : AppCompatActivity() {

    /**
     * variable to link main screen activity view
     */
    private lateinit var binding: ActivityMain2Binding

    /**
     * variable responsible for managing user data
     */
    private lateinit var userViewModel: UserViewModel
    /**
     * variable responsible for managing database data
     */
    private lateinit var mShopViewModel: ShopViewModel
    /**
     * variable responsible for managing theme data
     */
    private var sharedPreferences:SharedPreferences?=null
    /**
     * variable responsible for managing settings data
     */
    private  var settingsViewModel: SettingsViewModel? =null

    /**
     * function that is called when a new instance of a main activity class is created
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /**
         * user view model provider. Its getting user view model object from the application context
         */
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        /**
         * shop view model provider. Its getting shop view model object from the application context
         */
        mShopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]

        /**
         * settings view model provider. Its getting settings view model object from the application context
         */
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        /**
         * checking theme
         */
        sharedPreferences = applicationContext.getSharedPreferences("MODE", Context.MODE_PRIVATE)
        settingsViewModel!!.setThemeFlag(sharedPreferences?.getBoolean("night",false)!!)
        if(settingsViewModel!!.getThemeFlag()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        /**
         * Inflate the layout for this activity
         */
        binding = ActivityMain2Binding.inflate(layoutInflater)

        setContentView(binding.root)

        /**
         * setting up navbar
         */
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        val basketBadge = navView.getOrCreateBadge(R.id.navigation_basket)
        basketBadge.setVisible(true)
        basketBadge.setNumber(SharedData.amountOfProductsInBasket.value!!)

        SharedData.amountOfProductsInBasket.observe(this, Observer {
            basketBadge.setNumber(it)
        })

        navView.setupWithNavController(navController)
    }

}