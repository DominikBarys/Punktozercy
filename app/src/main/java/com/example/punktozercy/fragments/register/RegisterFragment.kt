package com.example.punktozercy.fragments.register

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentRegisterBinding
import com.example.punktozercy.model.User
import com.example.punktozercy.viewModel.ShopViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


/**
 * class that is responsible for register fragment. It is used for register a new user and add
 * him to database
 * @property mShopViewModel
 * @property _binding
 * @property binding
 * @property onCreateView
 * @property insertDataToDatabase
 * @property checkUsername
 * @property checkInputData
 * @property checkEmail
 * @property checkPassword
 * @property checkRepeatPassword
 * @property checkPhoneNumber
 *
 */
class RegisterFragment : Fragment() {

    /**
     * variable responsible for managing database data
     */
    private lateinit var mShopViewModel: ShopViewModel

    /**
     * binding object
     */
    private var _binding: FragmentRegisterBinding? = null
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
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)

        /**
         * shop view model provider. Its getting shop view model object from the application context
         */
        mShopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]

        /**
         * inserting data to database after button is clicked
         */
        binding.button1.setOnClickListener{
            insertDataToDatabase()
        }

        /**
         * go to login fragment after text is clicked
         */
        binding.textLogin.setOnClickListener{
            Navigation.findNavController(requireView()).navigateUp()
        }

        /**
         * get the logo binding
         */
        val logo = binding.registerLogo

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


        /**
         * after text button is clicked go to terms cond fragment
         */
        binding.termsAndConditionsButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_termsAndConditionsFragment)
        }

        /**
         * after text button is clicked go to privacy policy fragment
         */
        binding.privacyPolicyButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_privacyPolicyFragment)
        }

        return binding.root
    }


    /**
     * function that is responsible for inserting data to database
     *
     */
    private fun insertDataToDatabase() {
        lifecycleScope.launch {
            if(checkInputData()){
                // create new user
                val user = User(0,binding.TextUsername.text.toString(),binding.TextPassword.text.toString()
                    ,binding.TextPhone.text.toString(),null,binding.TextEmailAddress.text.toString(),200,null)
                // add user to database
                mShopViewModel.addUser(user)
                // navigate to login fragment
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }


    /**
     * function that is responsible for checking username of the user. If the username is already taken
     * or text is empty its showing error to the user
     * @return true if the username is not taken or text is not empty, otherwise false
     */
    private suspend fun checkUsername():Boolean{
        val userName = binding.TextUsername.text.toString()
        if(userName.isNotEmpty()){
            val job = lifecycleScope.async {
                    if(mShopViewModel.isUserNameExists(userName)){
                        binding.TextUsername.error ="Username is already taken"
                        return@async false
                    }
                    else{
                        return@async true
                    }
                }
            return job.await()
        }
        else{
            binding.TextUsername.error= "Username cannot be empty"
            return false
        }

    }

    /**
     * function that is responsible for checking user input. It is checking username, email, password,
     * repeat password and phone number
     * @return true if all input is correct, otherwise false and it is showing errors to the user
     * that he need to fix
     */
    private suspend fun checkInputData():Boolean{
        val job1 = lifecycleScope.async {
            val job = lifecycleScope.async{
                checkUsername()
                checkEmail()
                checkPassword()
                checkRepeatPassword()
                checkPhoneNumber()
                return@async checkUsername() && checkEmail() && checkPassword() && checkRepeatPassword() && checkPhoneNumber()
            }
            return@async job.await()


        }
        return job1.await()
    }

    /**
     * function that is responsible for checking user email using regex.
     * If the email is already taken or text is empty or it is invalid,
     * its showing error to the user
     * @return true if the user email is not taken or text is not empty or email is
     * valid, otherwise false
     */
    private suspend fun checkEmail():Boolean{
        val email = binding.TextEmailAddress.text.toString()
        val emailRegex = "^[a-zA-Z\\d._%+-]+@[a-zA-Z\\d.-]+\\.(com|pl)\$"
        if(email.isNotEmpty()){
            val job = lifecycleScope.async {
                if (mShopViewModel.isUserEmailExists(email)) {
                    binding.TextEmailAddress.error = "Email is already taken"
                    return@async false
                } else {
                        if(email.matches(emailRegex.toRegex())){

                            return@async true
                        }
                    else{
                            binding.TextEmailAddress.error = "Email address is invalid"
                            return@async false
                        }

                }

            }
            return job.await()
        }else{
            binding.TextEmailAddress.error="Email cannot be empty"
            return false
        }
    }

    /**
     * function that is responsible for checking user password using regex. If the password do not
     * match the regex it is showing error to the user
     * @return true if the user password is correct, otherwise false
     */
    private fun checkPassword():Boolean{
        val password = binding.TextPassword.text.toString()
        val passwordRegex = "^(?=.*[A-Z])(?=.*\\d).{6,}\$"
        return if(password.matches(passwordRegex.toRegex())){
            true
        } else{
            binding.TextPassword.error="Password should have minimum of 6 characters and contains minimum of " +
                    "1 capital letter and one number "
            false
        }

    }

    /**
     * function that is responsible for checking user repeat password. If the repeat password do not
     * match password then error is occurred
     * @return true if passwords match each other, otherwise false
     */
    private fun checkRepeatPassword():Boolean{
        val password = binding.TextPassword.text.toString()
        val repeatPassword = binding.TextRepeatPassword.text.toString()
        return if(password != repeatPassword || password.isEmpty()){
            binding.TextRepeatPassword.error = "Entered password don't match the previous one"
            false
        }else{
            true
        }
    }
    /**
     * function that is responsible for checking user phone number using regex. If the phone number
     * do not match regex it is showing error to the user
     * @return true if phone number match regex, otherwise false
     */
    private fun checkPhoneNumber():Boolean{
        val phone = binding.TextPhone.text.toString()
        val regex = Regex("^(?:\\+48|48)?(?:\\d{9})$")
        return if(regex.matches(phone)){
            true
        } else{
            binding.TextPhone.error="Number is invalid"
            false
        }
    }

}