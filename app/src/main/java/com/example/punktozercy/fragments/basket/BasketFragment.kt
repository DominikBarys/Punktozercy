package com.example.punktozercy.fragments.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punktozercy.R
import com.example.punktozercy.SelectedProducts
import com.example.punktozercy.databinding.FragmentBasketBinding
import com.example.punktozercy.fragments.basket.adapters.BasketAdapter

class BasketFragment : Fragment() {

    val selectedProducts: SelectedProducts = SelectedProducts()
    private var _binding: FragmentBasketBinding? = null
    lateinit var basketViewModel: BasketViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBasketBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        basketViewModel = ViewModelProvider(this)[BasketViewModel::class.java]


        val adapter = BasketAdapter(selectedProducts.getProductList(), requireContext())
        binding.basketRecycler.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.basketRecycler.adapter = adapter

//
//        binding.testButton.setOnClickListener {
//            Toast.makeText(requireContext(), selectedProducts.getTest(), Toast.LENGTH_SHORT).show()
//        }


        return binding.root
    }
}