package com.example.punktozercy.fragments.home.productslist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punktozercy.R
import com.example.punktozercy.databinding.FragmentProductsListBinding
import com.example.punktozercy.fragments.home.HomeViewModel
import com.example.punktozercy.fragments.home.adapters.ProductsAdapter
import com.example.punktozercy.model.Product
import com.example.punktozercy.viewModel.ShopViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProductsListFragment : Fragment() {

    private var _binding: FragmentProductsListBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var mShopViewModel: ShopViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]

        lifecycleScope.launch {
           val job =  lifecycleScope.async {
                 return@async  productViewModel.setProductList(mShopViewModel.getProductsByCategory(productViewModel.getCategory()))

           }
            job.await()

            val produkty:List<Product> = productViewModel.getProductList()
            Toast.makeText(requireContext(), productViewModel.getProductList().size.toString(), Toast.LENGTH_SHORT).show()
            val adapter = ProductsAdapter(productViewModel.getProductList(),requireContext())
            for(products in produkty){
                Log.d("MORENKA",products.name)
            }
            binding.productsView.layoutManager = LinearLayoutManager(requireContext())
            binding.productsView.adapter = adapter
        }

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