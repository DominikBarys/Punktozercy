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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions:GoogleSignInOptions


    private var _binding:FragmentLoginBinding? = null
    private lateinit var mShopViewModel: ShopViewModel
    private lateinit var userViewModel: UserViewModel
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        //ShopViewModel provider
        mShopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(activity!!, googleSignInOptions)
        // logout google account
        //googleSignInClient.revokeAccess()


        binding.signInButton.setOnClickListener{
            val account:GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(activity!!)
            if(account !=null){
                /*check if the google user exists in database. if yes then go to mainactivity2.
                if no then go to sign by google and create new user in database
                * */
                checkGoogleUser(account)
            }
            else
            {
                goToSignIn()
            }


        }


        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginButton.setOnClickListener{
            lifecycleScope.launch {
                if(checkUserLogin()){
                    val intent = Intent(activity,MainScreenActivity::class.java)
                    intent.putExtra("user", userViewModel.getUser())
                    activity?.startActivity(intent)
                }
            }
        }

        val logo = binding.applicationLogo
        logo.rotation = 15f // ustawienie początkowego kąta obrotu

        val handler = Handler()

        val runnable = object : Runnable {
            override fun run() {
                logo.animate().apply {
                    duration = 3000
                    rotationBy(-30f) // zmiana rotacji na -240 stopni (120 stopni w lewo)
                }.withEndAction{
                    logo.animate().apply {
                        duration = 3000
                        rotationBy(30f) // zmiana rotacji na 240 stopni (wraca do początkowego kąta)
                    }
                }
                handler.postDelayed(this, 6000) // Uruchamia się co 6 sekund
            }
        }
        handler.postDelayed(runnable, 100)

        return binding.root
    }

    private fun goToSignIn() {

        val signInIntent = googleSignInClient.signInIntent

        startActivityForResult(signInIntent,1000)
    }

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

    private fun goToHome() {
        val intent = Intent(activity, MainScreenActivity::class.java)
        activity?.startActivity(intent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val user = User(0, account.displayName!!,null,null,null,account.email!!,0,account.id)

            setGoogleUser(account,user)


        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(requireContext(), "signInResult:failed code=" + e.statusCode, Toast.LENGTH_SHORT).show()
        }
    }
    private suspend fun checkUserLogin():Boolean{
        val email = binding.TextEmailAddress.text.toString()
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

    private fun setGoogleUser(account: GoogleSignInAccount, user:User){
        lifecycleScope.launch {
            val users:List<User>
            val job = lifecycleScope.async{
                return@async  mShopViewModel.findUserByGoogleToken(account.id!!)
            }
            users =  job.await()
            if(users.isEmpty()){
                userViewModel.setUser(user)
                mShopViewModel.addUser(user)
            }else{
                userViewModel.setUser(users[0])
            }

        }
    }

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