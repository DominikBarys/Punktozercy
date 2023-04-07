package com.example.punktozercy.mainscreen

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.punktozercy.R
import com.example.punktozercy.databinding.ActivityMain2Binding
import com.example.punktozercy.viewModel.UserViewModel

class MainScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var userViewModel: UserViewModel
    private lateinit var mainScreenVm : MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        mainScreenVm = ViewModelProvider(this).get(MainScreenViewModel::class.java)
        //przekazanie usera do homeFragment

        //DEBUG
       // val user: User? = intent.getParcelableExtra("user")
       // userViewModel.setUser(user!!)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigationBar: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        var basketBadge = bottomNavigationBar.getOrCreateBadge(R.id.navigation_basket)
        basketBadge.setVisible(true)
        basketBadge.setNumber(mainScreenVm.amountOfProductsInBasket.value!!)

        mainScreenVm.amountOfProductsInBasket.observe(this, Observer {
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

        mainScreenVm.amountOfProductsInBasket.value = mainScreenVm.amountOfProductsInBasket.value!! + 1

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationBar.setupWithNavController(navController)
    }
}