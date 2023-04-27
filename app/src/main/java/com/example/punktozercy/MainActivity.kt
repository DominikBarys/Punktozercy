package com.example.punktozercy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * class responsible for a single screen that the user can see and interact with. It is an parent
 * of the login and register fragments
 */
class MainActivity : AppCompatActivity() {

    /**
     * function that is called when a new instance of a main activity class is created
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}