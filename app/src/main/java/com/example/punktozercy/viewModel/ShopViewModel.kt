package com.example.punktozercy.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.punktozercy.model.*
import com.example.punktozercy.repository.ShopRepository
import kotlinx.coroutines.*

/**
 * class responsible for storing and managing the data associated with the shop view
 * @property repository shop repository
 * @property addUserShoppingHistory
 * @property getUserShoppingHistory
 * @property getProductsByCategory
 * @property addUser
 * @property updateUserData
 * @property updateGoogleUserData
 * @property updateUserPoints
 * @property updateReceiverUserPoints
 * @property isUserLoginExists
 * @property findUserByGoogleToken
 * @property isUserNameExists
 * @property isUserEmailExists
 * @property getUserById
 */
class ShopViewModel(application: Application) : AndroidViewModel(application) {
   private val repository: ShopRepository

   init {
       val userDao = ShopDatabase.getDatabase(application).userDao()
       val productDao = ShopDatabase.getDatabase(application).productDao()
       val shoppingHistoryDao = ShopDatabase.getDatabase(application).shoppingHistoryDao()
       repository = ShopRepository(userDao, productDao, shoppingHistoryDao)


   }

    //-------------------------------------USER WITH PRODUCTS QUERY---------------------------------------------------
    /**
     * function used for inserting shopping history to shop repository
     * @param shoppingHistory shopping history object
     */
     fun addUserShoppingHistory(shoppingHistory: ShoppingHistory){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserShoppingHistory(shoppingHistory)
        }
    }
    /**
     * function used for getting user shopping history from shop repository
     * @param userId id of the user
     * @return list of user shopping history objects
     */
    suspend fun getUserShoppingHistory(userId:Long): List<UserShoppingHistoryWithProduct>{
        return withContext(Dispatchers.IO) {
            return@withContext repository.getUserShoppingHistory(userId)
        }
    }

    //--------------------------------------PRODUCT FUNCTIONS---------------------------------------------------------

    /**
     * function responsible for getting a list of products by category type from shop repository
     * @param category name of the category
     * @return list of Product objects
     */
    suspend fun getProductsByCategory(category: String):List<Product>{
        return withContext(Dispatchers.IO) {
            return@withContext repository.getProductsByCategory(category)
        }
    }

    //---------------------------------------USER FUNCTIONS-----------------------------------------------------------
    /**
     * function responsible for adding a user to shop repository and returning he is actual id
     * @param user User object
     * @return user id
     */
    suspend fun addUser(user:User):Long{
        return withContext(Dispatchers.IO) {
            return@withContext repository.addUser(user)
        }
    }
    /**
     * function responsible for updating user data in shop repository
     * @param userid user id
     * @param username username of the user
     * @param Email user email
     * @param Password user password
     * @param phone user phone number
     * @param Address user address
     */
    fun updateUserData(userid:Long,username:String,Email:String,Password:String?,phone:String?,Address:String?){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateUserData(userid, username, Email, Password, phone, Address)
        }
    }
    /**
     * function responsible for updating user data signed by google in shop repository
     * @param userid user id
     * @param Username username of the user
     * @param Email user email
     * @param Password user password
     * @param phone user phone number
     * @param Address user address
     * @param token google token
     */
     fun updateGoogleUserData(userid:Long,Username:String,Email:String,Password:String?,phone:String?,Address:String?,token:String?){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateGoogleUserData(userid, Username, Email, Password, phone, Address, token)
        }
    }
    /**
     * function responsible for updating user points in shop repository
     * @param userid user id
     * @param points number of points to which it will be updated
     */
     fun updateUserPoints(userid: Long,points:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUserPoints(userid,points)
        }
    }
    /**
     * function responsible for updating user points in shop repository
     * of the user that will receive points
     * @param email user email
     * @param points number of points that will be added to his current points
     */

     fun updateReceiverUserPoints(email: String,points: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateReceiverUserPoints(email,points)
        }
    }
    /**
     * function responsible for checking if the user exists in shop repository when he is trying
     * to log in
     * @param _email user email
     * @param _password user password
     * @return list of the User objects
     */
     @OptIn(ExperimentalCoroutinesApi::class)
     suspend fun isUserLoginExists(_email:String, _password:String):List<User>{
         return withContext(Dispatchers.IO) {
             return@withContext repository.isUserLoginExists(_email, _password)
         }
     }
    /**
     * function responsible for getting user by google token from shop repository
     * @param token google token
     * @return list of the User objects
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun findUserByGoogleToken(token:String):List<User>{
        return withContext(Dispatchers.IO) {
            return@withContext repository.findUserByGoogleToken(token)
        }
    }
    /**
     * function responsible for checking if the username exists in shop repository
     * @param username username of the user
     * @return true if the user exists, otherwise false
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun isUserNameExists(username:String):Boolean{
        return withContext(Dispatchers.IO) {
            return@withContext repository.isUserNameExists(username)
        }
    }
    /**
     * function responsible for checking if the user email exists in shop repository
     * @return true if the user email exists, otherwise false
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun isUserEmailExists(_email:String):Boolean{
        return withContext(Dispatchers.IO) {
            return@withContext repository.isUserEmailExists(_email)
        }
    }
    /**
     * function responsible for getting user id from shop repository
     * @param userid user id
     * @return User object
     */

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getUserById(userid: Long):User{
        return withContext(Dispatchers.IO) {
            return@withContext repository.getUserById(userid)
        }
    }



}