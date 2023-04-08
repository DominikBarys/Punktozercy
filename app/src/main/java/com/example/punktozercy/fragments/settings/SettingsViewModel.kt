package com.example.punktozercy.fragments.settings

import androidx.lifecycle.ViewModel

class SettingsViewModel:ViewModel() {

    private  var themeFlag:Boolean = false

     fun setThemeFlag(flag:Boolean){
        themeFlag = flag
    }
     fun getThemeFlag():Boolean{
        return themeFlag
    }

}