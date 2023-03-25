package com.example.punktozercy.fragments.register

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentRegisterBinding
import com.example.punktozercy.model.User
import com.example.punktozercy.viewModel.ShopViewModel

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
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        return binding.root;
    }

    private fun insertDataToDatabase() {
        val userName = binding.TextUsername.text.toString()
        val email = binding.TextEmailAddress.text.toString()
        val password = binding.TextPassword.text.toString()
        val repeatPassword = binding.TextRepeatPassword.text.toString();
        val phone = binding.TextPhone.text.toString();

        if(checkInput(userName,email, password, repeatPassword, phone)){
            //create User object
            val user = User(0,userName,password,phone,null,email,0);
            mShopViewModel.addUser(user);

            Toast.makeText(requireContext(),"Added User",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields",Toast.LENGTH_LONG).show()
        }
    }


    private fun checkInput(username:String,email:String,password:String,repeatPassword:String,phone:String):Boolean{
            return !(TextUtils.isEmpty(username) && TextUtils.isEmpty(email)&& TextUtils.isEmpty(password)&&
                    TextUtils.isEmpty(repeatPassword)&& TextUtils.isEmpty(phone))
    }

}