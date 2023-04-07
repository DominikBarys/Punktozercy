package com.example.punktozercy.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.punktozercy.databinding.FragmentHomeBinding
import com.example.punktozercy.mainscreen.MainScreenViewModel
import com.example.punktozercy.model.User

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
   // private var mainScreenVm = activity?.let { ViewModelProvider(it)[MainScreenViewModel::class.java] }
   private lateinit var mainScreenVm: MainScreenViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
            mainScreenVm = ViewModelProvider(requireActivity())[MainScreenViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        binding.debugAddProductButton.setOnClickListener{
            mainScreenVm.amountOfProductsInBasket.value = mainScreenVm.amountOfProductsInBasket.value!! + 1
        }


        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}