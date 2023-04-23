package com.example.punktozercy.viewModel

import androidx.lifecycle.ViewModel
import com.example.punktozercy.model.User

//
class UserViewModel:ViewModel() {


    companion object{
        private lateinit var user: User

    }

    fun setUser(_user:User){
        user = _user
    }

    fun getUser():User{
        return user
    }

    fun getEmail():String{
        return user.email
    }
    fun getUsername():String{
        return user.userName
    }

    fun getPassword(): String? {
        return user.password
    }

    fun getAddress(): String? {
        return user.address
    }

    fun getPoints():Int{
        return user.points
    }

    fun getGoogleToken(): String? {
        return user.googleToken
    }

    fun getPhoneNumber(): String? {
        return user.phoneNumber
    }
    fun getUserId():Long{
        return user.userId
    }


}