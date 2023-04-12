package com.example.punktozercy.fragments.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentEditProfileBinding
import com.example.punktozercy.databinding.FragmentLoginBinding
import com.example.punktozercy.databinding.FragmentSettingsBinding
import com.example.punktozercy.model.User
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel
import kotlinx.coroutines.*


class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private lateinit var mShopViewModel: ShopViewModel
    private lateinit var userViewModel: UserViewModel
    private val binding get() = _binding!!
    private lateinit var user:User


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)

        //ShopViewModel provider
        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        //UserViewModel provider
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        //setting text labels from data user
        loadDataFromUser()


        //buttons listeners
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.save.setOnClickListener {

                lifecycleScope.launch {
                    if(userViewModel.getGoogleToken() != null){
                            if(checkUserLoggedByGoogle()){
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

                                findNavController().navigateUp()
                            }
                    }
                    else if(checkUsername() && checkEmail() && checkPassword() && checkRepeatPassword() && checkPhoneNumber()) {
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

                        findNavController().navigateUp()
                    }
                }
        }



        return binding.root;
    }
    //setting text labels from data user
    private fun loadDataFromUser(){
        user = userViewModel.getUser()

        binding.TextEmailAddress.setText(user.email)
        binding.TextUsername.setText(user.userName)
        binding.TextPassword.setText(user.password)
        binding.TextPhone.setText(user.phoneNumber)
        binding.Address.setText(user.address)
//        binding.TextEmailAddress.isEnabled = false

    }


    //checks username of user
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
    //checks password
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

    suspend fun checkUserLoggedByGoogle():Boolean{
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