package com.example.punktozercy.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentSettingsBinding
import com.example.punktozercy.model.User
import com.example.punktozercy.viewModel.UserViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private lateinit var userViewModel: UserViewModel
    private val binding get() = _binding!!
    private var nightMode:Boolean = false
    private var editor:SharedPreferences.Editor?=null
    private var sharedPreferences:SharedPreferences?=null
    private  var settingsViewModel: SettingsViewModel? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        settingsViewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]
        //init text values using user attributes
        init()

        //caching and checking user theme
        sharedPreferences = activity?.getSharedPreferences("MODE", Context.MODE_PRIVATE)
        settingsViewModel!!.setThemeFlag(sharedPreferences?.getBoolean("night",false)!!)
        if(settingsViewModel!!.getThemeFlag()){
            binding.switchTheme.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        checkTheme()

        return binding.root
    }
    private fun init(){
        binding.username.text = userViewModel.getUsername()
        binding.email.text = userViewModel.getEmail()
        binding.points.text = userViewModel.getPoints().toString()
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


