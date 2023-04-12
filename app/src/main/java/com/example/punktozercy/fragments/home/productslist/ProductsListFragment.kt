package com.example.punktozercy.fragments.home.productslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentProductsListBinding
import com.example.punktozercy.fragments.home.HomeViewModel

class ProductsListFragment : Fragment() {

    private var _binding: FragmentProductsListBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var productViewModel: ProductViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        binding.textView6.text = productViewModel.getCategory()

        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
        }

        binding.showButton.setOnClickListener {
            Toast.makeText(requireContext(), productViewModel.getCategory(), Toast.LENGTH_SHORT).show()
        }


        // Inflate the layout for this fragment
        return binding.root
    }

}