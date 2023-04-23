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
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.punktozercy.R
import com.example.punktozercy.SelectedProducts
import com.example.punktozercy.databinding.ActivityMain2Binding
import com.example.punktozercy.fragments.settings.SettingsViewModel
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel

class MainScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var userViewModel: UserViewModel
    private lateinit var mainScreenViewModel: MainScreenViewModel
    private lateinit var mShopViewModel: ShopViewModel
    private var sharedPreferences:SharedPreferences?=null
    private  var settingsViewModel: SettingsViewModel? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedProducts: SelectedProducts = SelectedProducts()

        //user model provider
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        //shop model provider
        mShopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]

        //checking user Theme
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        sharedPreferences = applicationContext.getSharedPreferences("MODE", Context.MODE_PRIVATE)
        settingsViewModel!!.setThemeFlag(sharedPreferences?.getBoolean("night",false)!!)
        if(settingsViewModel!!.getThemeFlag()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        mainScreenViewModel = ViewModelProvider(this)[MainScreenViewModel::class.java]
        binding = ActivityMain2Binding.inflate(layoutInflater)

        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        val basketBadge = navView.getOrCreateBadge(R.id.navigation_basket)
        basketBadge.setVisible(true)
        basketBadge.setNumber(SelectedProducts.amountOfProductsInBasket.value!!)
        //basketBadge.backgroundColor = 23

        SelectedProducts.amountOfProductsInBasket.observe(this, Observer {
            basketBadge.setNumber(it)
        })
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_basket,
                R.id.navigation_shopping_history,
                R.id.navigation_settings
            )
        )

        navView.setupWithNavController(navController)
    }

}