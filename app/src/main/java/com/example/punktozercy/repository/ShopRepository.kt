package com.example.punktozercy.repository


import com.example.punktozercy.model.*

/**
 * class that represents shop repository responsible for performing operations on data
 * @param userDao user Dao object
 * @param productDao product Dao object
 * @param shoppingHistoryDao shopping history dao object
 * @property addUserShoppingHistory
 * @property getUserShoppingHistory
 * @property getProductsByCategory
 * @property addUser
 * @property updateReceiverUserPoints
 * @property updateUserPoints
 * @property updateGoogleUserData
 * @property getUserById
 * @property updateUserData
 * @property isUserEmailExists
 * @property isUserLoginExists
 * @property findUserByGoogleToken
 * @property isUserNameExists
 */
class ShopRepository(private val userDao: UserDao,private val productDao: ProductDao,private val shoppingHistoryDao: ShoppingHistoryDao) {

//---------------------------------------USER WITH PRODUCTS QUERY-------------------------------------------------

    /**
     * function used for inserting shopping history to database
     * @param shoppingHistory shopping history object
     */
     fun addUserShoppingHistory(shoppingHistory: ShoppingHistory){
        shoppingHistoryDao.addUserShoppingHistory(shoppingHistory)
    }
    /**
     * function used for getting user shopping history from database
     * @param userId id of the user
     * @return list of user shopping history objects
     */
     fun getUserShoppingHistory(userId:Long): List<UserShoppingHistoryWithProduct>{
        return shoppingHistoryDao.getUserShoppingHistory(userId)
    }

//-------------------------------------PRODUCT QUERY------------------------------------------------------
    /**
     * function responsible for getting a list of products by category type from database
     * @param category name of the category
     * @return list of Product objects
     */
     fun getProductsByCategory(category:String):List<Product>{
      return  productDao.getProductsByCategory(category)
    }




    //----------------------------USER QUERY----------------------------------------------------------
    /**
     * function responsible for adding a user to database and returning he is actual id
     * @param user User object
     * @return user id
     */
    suspend fun addUser(user: User):Long{
       return userDao.insertUser(user)
    }
    /**
     * function responsible for updating user points in database
     * of the user that will receive points
     * @param email user email
     * @param points number of points that will be added to his current points
     */
     fun updateReceiverUserPoints(email: String,points: Int){
        return userDao.updateReceiverUserPoints(email,points)
    }
    /**
     * function responsible for updating user points in database
     * @param userid user id
     * @param points number of points to which it will be updated
     */
     fun updateUserPoints(userid: Long,points:Int){
        return userDao.updateUserPoints(userid,points)
    }
    /**
     * function responsible for updating user data signed by google in database
     * @param userid user id
     * @param Username username of the user
     * @param Email user email
     * @param Password user password
     * @param phone user phone number
     * @param Address user address
     * @param token google token
     */
     fun updateGoogleUserData(userid:Long,Username:String,Email:String,Password:String?,phone:String?,Address:String?,token:String?){
        return userDao.updateGoogleUserData(userid, Username, Email, Password, phone, Address, token)
    }
    /**
     * function responsible for getting user id from database
     * @param userid user id
     * @return User object
     */
     fun getUserById(userid:Long):User{
       return userDao.getUserById(userid)
    }
    /**
     * function responsible for updating user data in database
     * @param userid user id
     * @param username username of the user
     * @param Email user email
     * @param Password user password
     * @param phone user phone number
     * @param Address user address
     */
     fun updateUserData(userid:Long,username:String,Email:String,Password:String?,phone:String?,Address:String?){
        userDao.updateUserData(userid,username,Email, Password, phone, Address)
    }

    /**
     * function responsible for checking if the user email exists in database
     * @return true if the user email exists, otherwise false
     */
     fun isUserEmailExists(email:String):Boolean{
       return userDao.isUserEmailExists(email)
    }
    /**
     * function responsible for checking if the user exists in database when he is trying
     * to log in
     * @param _email user email
     * @param _password user password
     * @return list of the User objects
     */
      fun isUserLoginExists(_email:String,_password:String):List<User>{
       return  userDao.isUserLoginExists(_email,_password)
    }
    /**
     * function responsible for getting user by google token from database
     * @param token google token
     * @return list of the User objects
     */
     fun findUserByGoogleToken(token:String):List<User>{
        return userDao.findUserByGoogleToken(token)
    }
    /**
     * function responsible for checking if the username exists in database
     * @param username username of the user
     * @return true if the user exists, otherwise false
     */
     fun isUserNameExists(username:String):Boolean{
        return userDao.isUserNameExists(username)
    }

}