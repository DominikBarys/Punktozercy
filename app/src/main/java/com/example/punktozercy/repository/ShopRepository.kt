package com.example.punktozercy.repository


import com.example.punktozercy.model.*

class ShopRepository(private val userDao: UserDao,private val productDao: ProductDao,private val shoppingHistoryDao: ShoppingHistoryDao) {

//---------------------------------------USER WITH PRODUCTS QUERY-------------------------------------------------

     fun addUserShoppingHistory(shoppingHistory: ShoppingHistory){
        shoppingHistoryDao.addUserShoppingHistory(shoppingHistory)
    }

     fun getUserShoppingHistory(userId:Long): List<UserShoppingHistoryWithProduct>{
        return shoppingHistoryDao.getUserShoppingHistory(userId)
    }

//-------------------------------------PRODUCT QUERY------------------------------------------------------

     fun getProductsByCategory(category:String):List<Product>{
      return  productDao.getProductsByCategory(category)
    }




    //----------------------------USER QUERY----------------------------------------------------------
    suspend fun addUser(user: User){
        userDao.insertUser(user)
    }

     fun updateUsers(user:User){}

     fun updateReceiverUserPoints(email: String,points: Int){
        return userDao.updateReceiverUserPoints(email,points)
    }

     fun updateUserPoints(userid: Long,points:Int){
        return userDao.updateUserPoints(userid,points)
    }

     fun updateGoogleUserData(userid:Long,Username:String,Email:String,Password:String?,phone:String?,Address:String?,token:String?){
        return userDao.updateGoogleUserData(userid, Username, Email, Password, phone, Address, token)
    }

     fun getUserById(userid:Long):User{
       return userDao.getUserById(userid)
    }
     fun updateUserData(userid:Long,username:String,Email:String,Password:String?,phone:String?,Address:String?){
        userDao.updateUserData(userid,username,Email, Password, phone, Address)
    }

    //TODO validacja przez email
     fun isUserEmailExists(email:String):Boolean{
       return userDao.isUserEmailExists(email)
    }
      fun isUserLoginExists(_email:String,_password:String):List<User>{
       return  userDao.isUserLoginExists(_email,_password)
    }

     fun findUserByGoogleToken(token:String):List<User>{
        return userDao.findUserByGoogleToken(token)
    }

     fun isUserNameExists(username:String):Boolean{
        return userDao.isUserNameExists(username)
    }

}