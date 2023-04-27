package com.example.punktozercy.fragments.home.productslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punktozercy.databinding.FragmentProductsListBinding
import com.example.punktozercy.fragments.home.HomeViewModel
import com.example.punktozercy.fragments.home.productslist.adapters.ProductsAdapter
import com.example.punktozercy.viewModel.ShopViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * class responsible for product list fragment. It is used when user click offer in home fragment
 * to show products of that category
 * @property _binding
 * @property homeViewModel
 * @property productViewModel
 * @property mShopViewModel
 * @property binding
 * @property onCreateView
 */
class ProductsListFragment : Fragment() {
    /**
     * binding object
     */
    private var _binding: FragmentProductsListBinding? = null
    /**
     * variable responsible for managing home data
     */
    private lateinit var homeViewModel: HomeViewModel
    /**
     * variable responsible for managing product data
     */
    private lateinit var productViewModel: ProductViewModel
    /**
     * variable responsible for managing database data
     */
    private lateinit var mShopViewModel: ShopViewModel
    /**
     * variable to link main screen activity view
     */
    private val binding get() = _binding!!
    /**
     * function that is called when new instance of fragment is created
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * Inflate the layout for this fragment
         */
        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        /**
         * home view model provider. Its getting home view model object from the application context
         */
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        /**
         * product view model provider. Its getting product view model object from the application context
         */
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        /**
         * shop view model provider. Its getting shop view model object from the application context
         */
        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]


        /**
         * getting product list by category from database and creating adapter
         */
        lifecycleScope.launch {
            // getting product list by category from database
           val job =  lifecycleScope.async {
                 return@async  productViewModel.setProductList(mShopViewModel.getProductsByCategory(productViewModel.getCategory()))
           }
            job.await()

            // creating adapter
            val adapter = ProductsAdapter(productViewModel.getProductList(),requireContext())

            // setting layout
            binding.productsView.layoutManager = LinearLayoutManager(requireContext())
            // setting adapter
            binding.productsView.adapter = adapter
        }

        /**
         * back to home fragment after button is clicked
         */
        binding.backButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
        }


        return binding.root
    }

}