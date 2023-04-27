package com.example.punktozercy.fragments.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.punktozercy.MainActivity
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentSettingsBinding
import com.example.punktozercy.viewModel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

/**
 * class that is responsible for settings fragment. It is used by user to change theme or show his
 * data, also he can to from here to edit profile fragment or send points fragment or he can logout
 * his account
 * @property _binding
 * @property userViewModel
 * @property binding
 * @property editor
 * @property sharedPreferences
 * @property settingsViewModel
 * @property onCreateView
 * @property setLabelTexts
 * @property checkTheme
 *
 */
class SettingsFragment : Fragment() {
    /**
     * binding object
     */
    private var _binding: FragmentSettingsBinding? = null
    /**
     * user view model provider. Its getting user view model object from the application context
     */
    private lateinit var userViewModel: UserViewModel
    /**
     * variable to link main screen activity view
     */
    private val binding get() = _binding!!

    /**
     * variable which allows to edit data stored in SharedPreferences
     */
    private var editor:SharedPreferences.Editor?=null
    /**
     * variable responsible for managing theme data
     */
    private var sharedPreferences:SharedPreferences?=null
    /**
     * variable responsible for managing settings data
     */
    private  var settingsViewModel: SettingsViewModel? =null
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
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        /**
         * user view model provider. Its getting user view model object from the application context
         */
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        /**
         * settings view model provider. Its getting settings view model object from the application context
         */
        settingsViewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]

        /**
         * setting text labels from data user
         */
        setLabelTexts()


        /**
         * caching and checking user theme
         */
        sharedPreferences = activity?.getSharedPreferences("MODE", Context.MODE_PRIVATE)
        settingsViewModel!!.setThemeFlag(sharedPreferences?.getBoolean("night",false)!!)
        if(settingsViewModel!!.getThemeFlag()){
            binding.switchTheme.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        // check theme
        checkTheme()

        /**
         * go to edit profile fragment after button is clicked
         */
        binding.editProfile.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_settings_to_editProfileFragment)
        }
        /**
         * go to send points fragment after button is clicked
         */
        binding.sendPoints.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_settings_to_sendPointsFragment)
        }


        /**
         * logout user
         */
        binding.logoutButton.setOnClickListener {
            logOutUser()
        }

        return binding.root
    }


    /**
     * function that is responsible for setting text labels from user data
     */
    private fun setLabelTexts(){

        binding.username.text = userViewModel.getUsername()
        binding.email.text =userViewModel.getEmail()
        binding.points.text = userViewModel.getPoints().toString()
        binding.Address.text = userViewModel.getAddress()
        binding.TextPhone.text = userViewModel.getPhoneNumber()

    }

    /**
     * function that is responsible for checking theme. If the switch is turned on (true) then put to
     * editor object false and set night theme , otherwise true and set light theme
     *
     */
    private fun checkTheme(){
        binding.switchTheme.setOnCheckedChangeListener { _, _ ->
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

    /**
     * function that is responsible for logout user. If the user is logged by google then revoke
     * access by using google sign in client and go to main activity
     */
    private fun logOutUser(){
        //check if the user is logged by google if not then start main activity
        if(userViewModel.getGoogleToken() == null){
            val intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)
        }
        // otherwise
        else{
            // create google options
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(userViewModel.getGoogleToken()!!)
                .requestEmail()
                .build()
            //get google object
            val mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
            // revoke access (logout google user)
            mGoogleSignInClient.revokeAccess()
            // start main activity
            val intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)
        }
    }

}


