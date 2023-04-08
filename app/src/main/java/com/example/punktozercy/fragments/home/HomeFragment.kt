package com.example.punktozercy.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.punktozercy.databinding.FragmentHomeBinding
import com.example.punktozercy.mainscreen.MainScreenViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
  //  private var mainScreenVm = activity?.let { ViewModelProvider(it)[MainScreenViewModel::class.java] }
   private lateinit var mainScreenVm: MainScreenViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        mainScreenVm = ViewModelProvider(requireActivity())[MainScreenViewModel::class.java]

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