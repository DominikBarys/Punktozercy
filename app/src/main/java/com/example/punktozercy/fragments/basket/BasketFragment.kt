package com.example.punktozercy.fragments.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
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

        basketViewModel = ViewModelProvider(this)[BasketViewModel::class.java]

        if(SelectedProducts.productList.size > 0 && !SelectedProducts.firstProduct)
        {
            SelectedProducts.textMoneyPrice.value = 0.0
            for(item in SelectedProducts.productList){
                SelectedProducts.textMoneyPrice.value = SelectedProducts.textMoneyPrice.value?.plus(item.price)
            }
        }else{
            SelectedProducts.textMoneyPrice.value = 0.0
        }

        SelectedProducts.textMoneyPrice.observe(this,{
            binding.moneyPriceTextView.text = "Price in zł: " + SelectedProducts.textMoneyPrice.value.toString() + "zł"
        })

        SelectedProducts.basketText.observe(this, {
            binding.emptyBasketText.text = SelectedProducts.basketText.value
        })

        val adapter = BasketAdapter(selectedProducts.getProductList(), requireContext())
        binding.basketRecycler.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.basketRecycler.adapter = adapter

        if(SelectedProducts.firstProduct){
            binding.basketRecycler.isVisible = false
        }else{
            binding.basketRecycler.isVisible = true
        }

        return binding.root
    }
}