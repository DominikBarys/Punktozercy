package com.example.punktozercy.fragments.TermsConditionsPrivacyPolicy

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.punktozercy.databinding.FragmentPrivacyPolicyBinding

/**
 * class that is responsible for privacy policy fragment. It is used to show user privacy policy of
 * the application
 */
class PrivacyPolicyFragment : Fragment() {
    /**
     * binding object
     */
    private var _binding: FragmentPrivacyPolicyBinding? = null
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
        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)

        // setting text
        binding.privacyPolicyText.movementMethod = ScrollingMovementMethod()

        // back to register fragment
        binding.acceptButton.setOnClickListener{
            Navigation.findNavController(requireView()).navigateUp()
        }



        return binding.root
    }
}