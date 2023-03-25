package com.example.punktozercy.fragments.login

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentLoginBinding
import com.example.punktozercy.viewModel.ShopViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding:FragmentLoginBinding? = null
    private lateinit var mShopViewModel: ShopViewModel
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        //ShopViewModel provider
        mShopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]

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
        /*Function that checks if the froms are not empty*/
    private fun checkInput(email:String,password:String):Boolean{
        return !(TextUtils.isEmpty(email)&& TextUtils.isEmpty(password))
    }

    private fun checkUserLogin(){

        val email = binding.TextEmailAddress.text.toString();
        val password = binding.TextPassword.text.toString();
        if(checkInput(email,password)){

            lifecycleScope.launch{
                if(mShopViewModel.isUserLoginExists(email,password)){
                    Toast.makeText(requireContext(),"Udalo sie zalogowac",Toast.LENGTH_LONG).show()

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