package com.example.punktozercy.fragments.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.punktozercy.R
import com.example.punktozercy.SelectedProducts
import com.example.punktozercy.databinding.FragmentBasketBinding
import com.example.punktozercy.databinding.FragmentHistoryBinding
import com.example.punktozercy.viewModel.ShopViewModel

class HistoryFragment : Fragment() {
    val selectedProducts: SelectedProducts = SelectedProducts()
    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        for(product in selectedProducts.getProductList()){
            Toast.makeText(requireContext(), product.name, Toast.LENGTH_SHORT).show()
        }

        return binding.root

    }
}