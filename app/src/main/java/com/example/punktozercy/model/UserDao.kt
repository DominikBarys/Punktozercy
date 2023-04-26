package com.example.punktozercy.model

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * UserDao interface responsible for interacting with the database
 * @property insertUser
 * @property insertUsers
 * @property updateUserData
 * @property updateGoogleUserData
 * @property updateUserPoints
 * @property updateReceiverUserPoints
 * @property getUserById
 * @property updateUsers
 * @property deleteUsers
 * @property deleteUser
 * @property loadAllUsers
 * @property isUserEmailExists
 * @property isUserLoginExists
 * @property findUserByGoogleToken
 * @property isUserNameExists
 */
@Dao
interface UserDao {

    /**
     * function responsible for adding a user to database
     * @param user User object
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user:User)
    /**
     * function responsible for adding users to database
     * @param users User objects
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users:User)
    /**
     * function responsible for updating user data in database
     * @param userid user id
     * @param Username username of the user
     * @param Email user email
     * @param Password user password
     * @param phone user phone number
     * @param Address user address
     */
    @Query("UPDATE users SET username= :Username, email= :Email, password= :Password, phone_number= :phone, address= :Address WHERE userId= :userid")
    fun updateUserData(userid:Long,Username:String,Email:String,Password:String?,phone:String?,Address:String?)
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
    @Query("UPDATE users SET username= :Username, email= :Email, password= :Password, phone_number= :phone, address= :Address, google_token = :token WHERE userId= :userid")
    fun updateGoogleUserData(userid:Long,Username:String,Email:String,Password:String?,phone:String?,Address:String?,token:String?)

    /**
     * function responsible for updating user points in database
     * @param userid user id
     * @param point number of points to which it will be updated
     */
    @Query("UPDATE users SET points = :point WHERE userId = :userid")
    fun updateUserPoints(userid:Long,point:Int)

    /**
     * function responsible for updating user points in database
     * of the user that will receive points
     * @param Email user email
     * @param point number of points that will be added to his current points
     */
    @Query("UPDATE users SET points = points +:point WHERE email = :Email")
    fun updateReceiverUserPoints(Email: String,point: Int)
    /**
     * function responsible for getting user id from database
     * @param userid user id
     * @return User object
     */
    @Query("SELECT * FROM users WHERE userId = :userid")
    fun getUserById(userid:Long):User
    /**
     * function responsible for updating user in database
     * @param user user object
     */
    @Update
    fun updateUsers(user:User)
    /**
     * function responsible for deleting users from database
     * @param users users object
     */
    @Delete
    fun deleteUsers(vararg users: User)
    /**
     * function responsible for deleting user from database
     * @param user user object
     */
    @Delete
    fun deleteUser(user: User)
    /**
     * function responsible for getting users from database
     * @return list of the User objects
     */
    @Query("SELECT * FROM users")
    fun loadAllUsers(): LiveData<List<User>>

    /**
     * function responsible for checking if the user email exists in database
     * @return true if the user email exists, otherwise false
     */
    @Query("SELECT EXISTS(SELECT email FROM users WHERE :email = email) ")
    fun isUserEmailExists(email:String):Boolean
    /**
     * function responsible for checking if the user exists in database when he is trying
     * to log in
     * @param em user email
     * @param pass user password
     * @return list of the User objects
     */
    @Query("SELECT * FROM users WHERE :em = email AND :pass = password")
    fun isUserLoginExists(em: String,pass:String):List<User>
    /**
     * function responsible for getting user by google token from database
     * @param token google token
     * @return list of the User objects
     */
    @Query("SELECT * FROM users WHERE :token = google_token")
    fun findUserByGoogleToken(token:String):List<User>
    /**
     * function responsible for checking if the username exists in database
     * @param userName username of the user
     * @return true if the user exists, otherwise false
     */
    @Query("SELECT EXISTS(SELECT username FROM users WHERE username = :userName )")
    fun isUserNameExists(userName:String):Boolean
}