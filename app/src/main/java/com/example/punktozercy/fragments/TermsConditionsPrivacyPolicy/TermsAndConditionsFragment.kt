package com.example.punktozercy.fragments.TermsConditionsPrivacyPolicy

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.punktozercy.databinding.FragmentTermsAndConditionsBinding


class TermsAndConditionsFragment : Fragment() {

    private var _binding: FragmentTermsAndConditionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTermsAndConditionsBinding.inflate(inflater, container, false)

        binding.termsAndConditionsText.movementMethod = ScrollingMovementMethod()

        binding.acceptButton.setOnClickListener{
            Navigation.findNavController(requireView()).navigateUp()
        }


        // Inflate the layout for this fragment
        return binding.root
    }

}