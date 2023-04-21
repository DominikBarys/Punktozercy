package com.example.punktozercy.fragments.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.punktozercy.MainActivity
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentSettingsBinding
import com.example.punktozercy.mainscreen.MainScreenActivity
import com.example.punktozercy.model.User
import com.example.punktozercy.viewModel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private lateinit var userViewModel: UserViewModel
    private val binding get() = _binding!!
    private var nightMode:Boolean = false
    private var editor:SharedPreferences.Editor?=null
    private var sharedPreferences:SharedPreferences?=null
    private  var settingsViewModel: SettingsViewModel? =null
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        settingsViewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]
        //init text values using user model attributes
        setLabelTexts()



        //caching and checking user theme
        sharedPreferences = activity?.getSharedPreferences("MODE", Context.MODE_PRIVATE)
        settingsViewModel!!.setThemeFlag(sharedPreferences?.getBoolean("night",false)!!)
        if(settingsViewModel!!.getThemeFlag()){
            binding.switchTheme.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        checkTheme()

        //buttons listeners
        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_settings_to_editProfileFragment)
        }

        binding.sendPoints.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_settings_to_sendPointsFragment)
        }

        binding.logoutButton.setOnClickListener {
            if(userViewModel.getGoogleToken() == null){
                val intent = Intent(activity, MainActivity::class.java)
                activity?.startActivity(intent)
            }else{
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(userViewModel.getGoogleToken()!!)
                    .requestEmail()
                    .build()

                val mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
                mGoogleSignInClient.revokeAccess()
                val intent = Intent(activity, MainActivity::class.java)
                activity?.startActivity(intent)
            }
        }

        return binding.root
    }



    private fun setLabelTexts(){

        binding.username.text = userViewModel.getUsername()
        binding.email.text =userViewModel.getEmail()
        binding.points.text = userViewModel.getPoints().toString()
        binding.Address.text = userViewModel.getAddress()
        binding.TextPhone.text = userViewModel.getPhoneNumber()

    }

    private fun checkTheme(){
        binding.switchTheme.setOnCheckedChangeListener { compoundButton, b ->
            if(settingsViewModel!!.getThemeFlag()){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor=sharedPreferences?.edit()
                editor?.putBoolean("night",false)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor=sharedPreferences?.edit()
                editor?.putBoolean("night",true)
            }
            editor?.apply()
        }
    }

}


