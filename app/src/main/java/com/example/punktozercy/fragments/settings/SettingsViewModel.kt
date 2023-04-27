package com.example.punktozercy.fragments.settings

import androidx.lifecycle.ViewModel

/**
 * class responsible for storing and managing the data associated with the settings view
 * @property themeFlag
 * @property setThemeFlag
 * @property getThemeFlag
 */

class SettingsViewModel:ViewModel() {

    /**
     * variable that is used to store theme. False means night theme, true - light theme
     */
    private  var themeFlag:Boolean = false


    /**
     * function that is responsbile for setting theme flag
     * @param flag false for night theme, true for light theme
     */
    fun setThemeFlag(flag:Boolean){
        themeFlag = flag
    }

    /**
     * function that returns theme flag
     * @return theme flag
     */
    fun getThemeFlag():Boolean{
        return themeFlag
    }

}