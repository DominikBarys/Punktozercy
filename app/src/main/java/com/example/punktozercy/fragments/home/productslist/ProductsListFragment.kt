package com.example.punktozercy.fragments.home.productslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punktozercy.databinding.FragmentProductsListBinding
import com.example.punktozercy.fragments.home.HomeViewModel
import com.example.punktozercy.fragments.home.productslist.adapters.ProductsAdapter
import com.example.punktozercy.mainscreen.MainScreenViewModel
import com.example.punktozercy.model.Product
import com.example.punktozercy.viewModel.ShopViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProductsListFragment : Fragment() {

    private var _binding: FragmentProductsListBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var mShopViewModel: ShopViewModel
    private lateinit var mainScreenVm: MainScreenViewModel

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
        mainScreenVm = ViewModelProvider(requireActivity())[MainScreenViewModel::class.java]

        lifecycleScope.launch {
           val job =  lifecycleScope.async {
                 return@async  productViewModel.setProductList(mShopViewModel.getProductsByCategory(productViewModel.getCategory()))
           }
            job.await()

            val adapter = ProductsAdapter(productViewModel.getProductList(),requireContext())

            binding.productsView.layoutManager = LinearLayoutManager(requireContext())
            binding.productsView.adapter = adapter
        }


        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
        }

        binding.showButton.setOnClickListener {
            Toast.makeText(requireContext(), productViewModel.getCategory(), Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

}