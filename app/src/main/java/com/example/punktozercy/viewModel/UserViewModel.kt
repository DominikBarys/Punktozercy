package com.example.punktozercy.viewModel

import androidx.lifecycle.ViewModel
import com.example.punktozercy.model.User

//
class UserViewModel:ViewModel() {

    private lateinit var user: User


    fun setUser(_user:User){
        user = _user
    }

    fun getUser():User{
        return user;
    }
}