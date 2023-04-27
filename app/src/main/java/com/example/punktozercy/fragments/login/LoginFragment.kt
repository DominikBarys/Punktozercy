package com.example.punktozercy.fragments.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.punktozercy.mainscreen.MainScreenActivity
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentLoginBinding
import com.example.punktozercy.model.User
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*
import java.lang.Runnable

/**
 * class that is responsible for login fragment. It is used to log in user to home fragment.
 * @property googleSignInClient
 * @property googleSignInOptions
 * @property _binding
 * @property mShopViewModel
 * @property userViewModel
 * @property binding
 * @property onCreateView
 * @property goToSignIn
 * @property onActivityResult
 * @property goToHome
 * @property handleSignInResult
 * @property checkUserLogin
 * @property checkGoogleUser
 * @property setGoogleUser
 */
class LoginFragment : Fragment() {


    /**
     * variable which is used to authenticate users using their Google account.
     */
    private lateinit var googleSignInClient: GoogleSignInClient

    /**
     * variable which is used to configure authentication options using a Google account.
     */
    private lateinit var googleSignInOptions:GoogleSignInOptions

    /**
     * binding object
     */
    private var _binding:FragmentLoginBinding? = null
    /**
     * variable responsible for managing database data
     */
    private lateinit var mShopViewModel: ShopViewModel
    /**
     * user view model provider. Its getting user view model object from the application context
     */
    private lateinit var userViewModel: UserViewModel
    /**
     * variable to link main screen activity view
     */
    private val binding get() = _binding!!
    /**
     * function that is called when new instance of fragment is created
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        /**
         * Inflate the layout for this fragment
         */
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        /**
         * shop view model provider. Its getting shop view model object from the application context
         */
        mShopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]


        /**
         * user view model provider. Its getting user view model object from the application context
         */
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        /**
         * Configure sign-in to request the user's ID, email address, and basic
         * profile. ID and basic profile are included in DEFAULT_SIGN_IN.
         */
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(activity!!, googleSignInOptions)


        /**
         * go to signing by google if the button is clicked
         */
        binding.signInButton.setOnClickListener{
            val account:GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(activity!!)
            if(account !=null){
                /*check if the google user exists in database. if yes then go to main screen activity.
                otherwise go to sign by google and create new user in database
                * */
                checkGoogleUser(account)
            }
            else
            {
                goToSignIn()
            }


        }

        /**
         * go to register fragment after button is clicked
         */
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        /**
         * if the user login is correct then go to the main screen acitivity after button is clicked
         */
        binding.loginButton.setOnClickListener{
            lifecycleScope.launch {
                if(checkUserLogin()){
                    val intent = Intent(activity,MainScreenActivity::class.java)
                    activity?.startActivity(intent)
                }
            }
        }

        /**
         * get the logo binding
         */
        val logo = binding.applicationLogo

        /**
         * setting the initial angle of rotation
         */
        logo.rotation = 15f

        val handler = Handler()

        val runnable = object : Runnable {
            override fun run() {
                logo.animate().apply {
                    duration = 3000
                    rotationBy(-30f) // change of rotation to -240 degrees (120 degrees to the left)
                }.withEndAction{
                    logo.animate().apply {
                        duration = 3000
                        rotationBy(30f) // change of rotation to 240 degrees (returns to the initial angle)
                    }
                }
                handler.postDelayed(this, 6000) // Starts every 6 seconds
            }
        }
        handler.postDelayed(runnable, 100)

        return binding.root
    }

    /**
     * function that is responsible for creating/going to new activity -  google signing window
     */
    private fun goToSignIn() {

        val signInIntent = googleSignInClient.signInIntent

        startActivityForResult(signInIntent,1000)
    }

    /**
     * function that is called after result of google signing  activity
     * @param requestCode request code
     * @param resultCode result code
     * @param data google user data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if(requestCode== 1000){
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                handleSignInResult(task)
                goToHome()
            }
            catch (e:java.lang.Exception){
                print(e.message)
            }
        }
    }

    /**
     * function that is responsible for creating/going to main screen activity
     */
    private fun goToHome() {
        val intent = Intent(activity, MainScreenActivity::class.java)
        activity?.startActivity(intent)
    }


    /**
     * function that is called to handle the user's Google account login results.
     * @param completedTask google task object
     */
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            //get google data to variable
            val account = completedTask.getResult(ApiException::class.java)
            // create new user
            val user = User(0, account.displayName!!,null,null,null,account.email!!,0,account.id)

            //set user view model and and user to database
            setGoogleUser(account,user)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(requireContext(), "signInResult:failed code=" + e.statusCode, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * function that is responsible for checking user login.
     * @return true if the login is correct, otherwise false and show errors to user
     */
    private suspend fun checkUserLogin():Boolean{
        // get email from text
        val email = binding.TextEmailAddress.text.toString()
        // get password from text
        val password = binding.TextPassword.text.toString()
        val job = lifecycleScope.async {
            val users: List<User>  = mShopViewModel.isUserLoginExists(email,password)

            if(users.isEmpty()){
                binding.TextEmailAddress.error="Invalid data. Try again"
                binding.TextPassword.error="Invalid data. Try again"
                return@async false
            }else if(users[0].password?.isEmpty() == true){
                binding.TextEmailAddress.error="Invalid data. Try again"
                binding.TextPassword.error="Invalid data. Try again"
                return@async false
            }else{
                userViewModel.setUser(users[0])
                return@async true
            }
        }
        return job.await()
    }


    /**
     * function that is responsible for adding a google user to database and setting user view model.
     * If user is not exists then add him to database and set user view model. If the user exists only
     * set user view model
     * @param account google account object
     * @param user user object
     */
    private fun setGoogleUser(account: GoogleSignInAccount, user:User){
        lifecycleScope.launch {
            val users:List<User>
            val job = lifecycleScope.async{
                return@async  mShopViewModel.findUserByGoogleToken(account.id!!)
            }
            users =  job.await()
            if(users.isEmpty()){
                val userId:Long
                userViewModel.setUser(user)
                val job2 = lifecycleScope.async {
                    return@async mShopViewModel.addUser(user)
                }
                userId = job2.await()

                userViewModel.setUserId(userId)

            }else{
                userViewModel.setUser(users[0])
            }

        }


    }

    /**
     * function that is responsible for checking if the google user exists in database. If the user
     * exists then go to main screen activity, otherwise go to signing by google
     */
    private fun checkGoogleUser(account:GoogleSignInAccount){
        lifecycleScope.launch {
            val users:List<User>
            val job = lifecycleScope.async{
                return@async  mShopViewModel.findUserByGoogleToken(account.id!!)
            }
            users =  job.await()
            if(users.isEmpty()){
                goToSignIn()
            }else
            {
                userViewModel.setUser(users[0])
                if(account.id!! == users[0].googleToken){
                    goToHome()
                }
                else{
                    goToSignIn()
                }
            }
        }
    }


}