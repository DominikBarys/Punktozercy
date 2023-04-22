package com.example.punktozercy.fragments.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punktozercy.databinding.FragmentHistoryBinding
import com.example.punktozercy.fragments.history.adapters.HistoryAdapter
import com.example.punktozercy.fragments.home.productslist.adapters.ProductsAdapter
import com.example.punktozercy.model.Product
import com.example.punktozercy.model.ShoppingHistory
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private lateinit var mShopViewModel: ShopViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var userShoppingHistoryViewModel: HistoryViewModel
    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var lista:List<ShoppingHistory>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        userShoppingHistoryViewModel = ViewModelProvider(requireActivity())[HistoryViewModel::class.java]
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            val job = async {
                 return@async userShoppingHistoryViewModel.setUserShoppingHistory(
                     mShopViewModel.getUserShoppingHistory(userViewModel.getUserId())
                 )
            }
            job.await()

            val adapter = HistoryAdapter(userShoppingHistoryViewModel.getUserShoppingHistory().reversed(),requireContext())
            binding.productsList.layoutManager = LinearLayoutManager(requireContext())
            binding.productsList.adapter = adapter

        }

        return binding.root

    }
}