package com.example.punktozercy.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.punktozercy.databinding.FragmentEditProfileBinding
import com.example.punktozercy.model.User
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel
import kotlinx.coroutines.*


/**
 * class that is responsible for edit profile fragments. It is used by user to change he is data.
 * @property _binding
 * @property binding
 * @property mShopViewModel
 * @property userViewModel
 * @property user
 * @property onCreateView
 * @property loadDataFromUser
 * @property editUserProfile
 * @property editUserGoogleProfile
 * @property checkUsername
 * @property checkEmail
 * @property checkPassword
 * @property checkRepeatPassword
 * @property checkPhoneNumber
 * @property checkUserLoggedByGoogle
 */
class EditProfileFragment : Fragment() {
    /**
     * binding object
     */
    private var _binding: FragmentEditProfileBinding? = null
    /**
     * variable responsible for managing database data
     */
    private lateinit var mShopViewModel: ShopViewModel
    /**
     * variable responsible for managing user data
     */
    private lateinit var userViewModel: UserViewModel
    /**
     * variable to link main screen activity view
     */
    private val binding get() = _binding!!

    /**
     * user object
     */
    private lateinit var user:User

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
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)

        /**
         * shop view model provider. Its getting shop view model object from the application context
         */
        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        /**
         * user view model provider. Its getting user view model object from the application context
         */
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        /**
         * setting text labels from data user
         */
        loadDataFromUser()


        /**
         * back to settings fragment after button is clicked
         */
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        /**
         * save user data after button is clicked
         */
        binding.save.setOnClickListener {

                lifecycleScope.launch {
                    if(userViewModel.getGoogleToken() != null){
                        editUserGoogleProfile()
                    }
                    else if(checkUsername() && checkEmail() && checkPassword() && checkRepeatPassword() && checkPhoneNumber()) {
                        editUserProfile()
                    }
                }
        }



        return binding.root
    }

    /**
     * function that is responsible for setting text labels from user data
     */
    private fun loadDataFromUser(){
        user = userViewModel.getUser()

        binding.TextEmailAddress.setText(user.email)
        binding.TextUsername.setText(user.userName)
        binding.TextPassword.setText(user.password)
        binding.TextPhone.setText(user.phoneNumber)
        binding.Address.setText(user.address)

    }

    /**
     * function that updates user data in database and user view model
     */
    private suspend fun editUserProfile(){

        // update database
        val job = lifecycleScope.async {
            mShopViewModel.updateUserData(
                user.userId,
                binding.TextUsername.text.toString(),
                binding.TextEmailAddress.text.toString(),
                binding.TextPassword.text.toString(),
                binding.TextPhone.text.toString(),
                binding.Address.text.toString()
            )
        }
        job.await()
        // update user view model
        val job2 = lifecycleScope.async {

            while(true){
                userViewModel.setUser(mShopViewModel.getUserById(user.userId))
                if(userViewModel.getUsername() == binding.TextUsername.text.toString() && userViewModel.getEmail()
                    == binding.TextEmailAddress.text.toString() && userViewModel.getAddress() == binding.Address.text.toString()
                    && userViewModel.getPhoneNumber() == binding.TextPhone.text.toString() && userViewModel.getPassword() ==
                    binding.TextPassword.text.toString())
                    return@async true
            }
        }
        job2.await()

        //back to settings fragment
        findNavController().navigateUp()

    }

    /**
     * function that updates google user data in database and user view model
     */
    private suspend fun editUserGoogleProfile(){
        //check if the user is logged by google
        if(checkUserLoggedByGoogle()){
            // update database
            val job = lifecycleScope.async {
                mShopViewModel.updateGoogleUserData(
                    user.userId,
                    binding.TextUsername.text.toString(),
                    binding.TextEmailAddress.text.toString(),
                    binding.TextPassword.text.toString(),
                    binding.TextPhone.text.toString(),
                    binding.Address.text.toString(),
                    user.googleToken
                )
            }
            job.await()
            // update user view model
            val job2 = lifecycleScope.async {

                while(true){
                    userViewModel.setUser(mShopViewModel.getUserById(user.userId))
                    if(userViewModel.getUsername() == binding.TextUsername.text.toString() && userViewModel.getEmail()
                        == binding.TextEmailAddress.text.toString() && userViewModel.getAddress() == binding.Address.text.toString()
                        && userViewModel.getPhoneNumber() == binding.TextPhone.text.toString() && userViewModel.getPassword() ==
                        binding.TextPassword.text.toString())
                        return@async true
                }
            }
            job2.await()

            // back to settings fragment
            findNavController().navigateUp()
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
                if(userName == user.userName){
                    return@async true
                }else{
                    if(mShopViewModel.isUserNameExists(userName)){
                        binding.TextUsername.error ="Username is already taken"
                        return@async false
                    }
                    else{
                        return@async true
                    }
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
                if(email == user.email){
                    return@async true
                }else{
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
        return if(password != repeatPassword){
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

    /**
     * function that checks inputs of user logged by google. Because user is logged by google
     * he do not need to have password or phone number that is why the function is needed and must
     * be duplicated
     * @return true if inputs are correct, otherwise false and showing errors to the user
     */
    private suspend fun checkUserLoggedByGoogle():Boolean{
        if(checkUsername() && checkEmail()) {
            val password = binding.TextPassword.text.toString()
            val repeatPassword = binding.TextRepeatPassword.text.toString()
            val phone = binding.TextPhone.text.toString()
            if (password.isNotEmpty() || repeatPassword.isNotEmpty()){
                return checkRepeatPassword()
            }
            else if(phone.isNotEmpty()){
                return checkPhoneNumber()
            }
            return true
          }else{
              return false
        }

    }


}