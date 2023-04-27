package com.example.punktozercy.viewModel

import androidx.lifecycle.ViewModel
import com.example.punktozercy.model.User

/**
 * class responsible for storing and managing the data associated with the user view
 * @property user user object
 * @property setUser
 * @property getUser
 * @property getEmail
 * @property getUsername
 * @property getPassword
 * @property getAddress
 * @property getPoints
 * @property getGoogleToken
 * @property getPhoneNumber
 * @property getUserId
 */



class UserViewModel:ViewModel() {


    /**
     * user object
     */
    companion object{
        private lateinit var user: User

    }

    /**
     * function that is responsible for setting the user object
     * @param _user User object
     */
    fun setUser(_user:User){
        user = _user
    }
    /**
     * function that is responsible for getting the user object
     * @return user object
     */
    fun getUser():User{
        return user
    }
    /**
     * function that is responsible for getting the user email
     * @return user email
     */

    fun getEmail():String{
        return user.email
    }
    /**
     * function that is responsible for setting actual user id. Its used for setting actual
     * id of user in login fragment when user is signing by google
     * @return user email
     */
    fun setUserId(id:Long){
        user.userId = id
    }
    /**
     * function that is responsible for getting the username of the user object
     * @return username of the user
     */
    fun getUsername():String{
        return user.userName
    }
    /**
     * function that is responsible for getting the password of the user object
     * @return user password
     */
    fun getPassword(): String? {
        return user.password
    }
    /**
     * function that is responsible for getting the address of the user object
     * @return user address
     */
    fun getAddress(): String? {
        return user.address
    }
    /**
     * function that is responsible for getting points from user object
     * @return user points
     */
    fun getPoints():Int{
        return user.points
    }
    /**
     * function that is responsible for getting google token from the user object
     * @return google token
     */
    fun getGoogleToken(): String? {
        return user.googleToken
    }
    /**
     * function that is responsible for getting user phone number from the user object
     * @return user phone number
     */
    fun getPhoneNumber(): String? {
        return user.phoneNumber
    }
    /**
     * function that is responsible for getting user id from the user object
     * @return user id
     */
    fun getUserId():Long{
        return user.userId
    }


}