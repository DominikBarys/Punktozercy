package com.example.punktozercy.fragments.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.punktozercy.MainActivity2
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

               lifecycleScope.launch {
                   val user:User = mShopViewModel.findUserByGoogleToken(account.id!!)
                   userViewModel.setUser(user)
                   if(account.id!! == user.googleToken){
                       goToHome()
                   }
                   else{
                       goToSignIn()
                   }

               }
            }
            //TODO stworz nowego uzytkownika w bazie danych
            goToSignIn()
        }

        //TODO
        //binding.button1.setOnClickListener()
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginButton.setOnClickListener{
                checkUserLogin()
        }
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
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToHome() {
        val intent = Intent(activity,MainActivity2::class.java)
        intent.putExtra("user",userViewModel.getUser())
        activity?.startActivity(intent)

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val user = User(0, account.displayName!!,null,null,null,account.email!!,0,account.id)
            userViewModel.setUser(user)
            mShopViewModel.addUser(user)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(requireContext(), "signInResult:failed code=" + e.statusCode, Toast.LENGTH_SHORT).show()
        }
    }


    /*Function that checks if the froms are not empty*/
    private fun checkInput(email:String,password:String):Boolean{
        return !(TextUtils.isEmpty(email)&& TextUtils.isEmpty(password))
    }

    private fun checkUserLogin(){

        val email = binding.TextEmailAddress.text.toString()
        val password = binding.TextPassword.text.toString()
        if(checkInput(email,password)){

            lifecycleScope.launch{
                if(mShopViewModel.isUserLoginExists(email,password)){

                    Toast.makeText(requireContext(),"Udalo sie zalogowac",Toast.LENGTH_LONG).show()
                    val intent = Intent(activity,MainActivity2::class.java)
                    val user = User(0,"Konrad","123","6654234",null,"konr509@wp.pl",0,null)
                    intent.putExtra("user",user)
                    activity?.startActivity(intent)
                }else{
                    Toast.makeText(requireContext(),"Taki uzytkownik nie istnieje",Toast.LENGTH_LONG).show()
                }
            }

        }else
        {
            Toast.makeText(requireContext(),"Please fill out all fields",Toast.LENGTH_LONG).show()
        }
    }

}