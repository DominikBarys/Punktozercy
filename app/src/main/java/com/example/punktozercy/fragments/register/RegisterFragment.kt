package com.example.punktozercy.fragments.register

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

class RegisterFragment : Fragment() {

    private lateinit var mShopViewModel: ShopViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)

        mShopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]
        binding.button1.setOnClickListener{
            insertDataToDatabase()
        }

        binding.textLogin.setOnClickListener{
            //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            Navigation.findNavController(requireView()).navigateUp()
        }

        val logo = binding.registerLogo
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

        binding.termsAndConditionsButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_termsAndConditionsFragment)
        }

        binding.privacyPolicyButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_privacyPolicyFragment)
        }

        return binding.root;
    }

    private fun insertDataToDatabase() {
        lifecycleScope.launch {
            if(checkUsername() || checkEmail() || checkPassword() || checkRepeatPassword() || checkPhoneNumber()){
                val user = User(0,binding.TextUsername.text.toString(),binding.TextPassword.text.toString()
                    ,binding.TextPhone.text.toString(),null,binding.TextEmailAddress.text.toString(),0,null);
                mShopViewModel.addUser(user);
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

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
        if(regex.matches(phone)){
            return true
        }
        else{
            binding.TextPhone.error="Number is invalid"
            return false
        }
    }

}