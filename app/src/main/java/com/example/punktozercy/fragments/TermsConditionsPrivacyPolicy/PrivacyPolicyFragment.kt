package com.example.punktozercy.fragments.TermsConditionsPrivacyPolicy

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentPrivacyPolicyBinding

class PrivacyPolicyFragment : Fragment() {

    private var _binding: FragmentPrivacyPolicyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)

        binding.privacyPolicyText.movementMethod = ScrollingMovementMethod()

        binding.acceptButton.setOnClickListener{
            Navigation.findNavController(requireView()).navigateUp()
        }



        return binding.root
    }
}