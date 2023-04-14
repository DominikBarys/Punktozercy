package com.example.punktozercy.repository

import androidx.lifecycle.LiveData
import com.example.punktozercy.model.*

class ShopRepository(private val userDao: UserDao,private val productDao: ProductDao,private val shoppingHistoryDao: ShoppingHistoryDao) {

   // val loadAllUsers : LiveData<List<User>> = userDao.loadAllUsers();
    //val loadAllProducts: LiveData<List<Product>> = productDao.loadAllProducts();

    //TODO
    //val loadUsersWithProducts


//-------------------------------------PRODUCT QUERY------------------------------------------------------

    suspend fun getProductsByCategory(category:String):List<Product>{
      return  productDao.getProductsByCategory(category)
    }




    //----------------------------USER QUERY----------------------------------------------------------
    suspend fun addUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun updateUsers(user:User){}

    suspend fun updateReceiverUserPoints(email: String,points: Int){
        return userDao.updateReceiverUserPoints(email,points)
    }

    suspend fun updateUserPoints(userid: Long,points:Int){
        return userDao.updateUserPoints(userid,points)
    }

    suspend fun updateGoogleUserData(userid:Long,Username:String,Email:String,Password:String?,phone:String?,Address:String?,token:String?){
        return userDao.updateGoogleUserData(userid, Username, Email, Password, phone, Address, token)
    }

    suspend fun getUserById(userid:Long):User{
       return userDao.getUserById(userid)
    }
    suspend fun updateUserData(userid:Long,username:String,Email:String,Password:String?,phone:String?,Address:String?){
        userDao.updateUserData(userid,username,Email, Password, phone, Address)
    }

    //TODO validacja przez email
    suspend fun isUserEmailExists(email:String):Boolean{
       return userDao.isUserEmailExists(email)
    }
     suspend fun isUserLoginExists(_email:String,_password:String):List<User>{
       return  userDao.isUserLoginExists(_email,_password)
    }

    suspend fun findUserByGoogleToken(token:String):List<User>{
        return userDao.findUserByGoogleToken(token)
    }

    suspend fun isUserNameExists(username:String):Boolean{
        return userDao.isUserNameExists(username)
    }

}