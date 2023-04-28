package com.example.punktozercy.fragments.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punktozercy.databinding.FragmentHistoryBinding
import com.example.punktozercy.fragments.history.adapters.HistoryAdapter
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * class responsible for history fragment. It is used by user to let him show his shopping history
 * where he can see he is bought products with dates of buy and type of currency with he bought the
 * product
 * @property mShopViewModel
 * @property userViewModel
 * @property userShoppingHistoryViewModel
 * @property _binding
 * @property binding
 * @property onCreateView
 */
class HistoryFragment : Fragment() {

    /**
     * variable responsible for managing database data
     */
    private lateinit var mShopViewModel: ShopViewModel

    /**
     * variable responsible for managing user data
     */
    private lateinit var userViewModel: UserViewModel

    /**
     * variable responsible for managing shopping history data
     */
    private lateinit var userShoppingHistoryViewModel: HistoryViewModel

    /**
     * binding object
     */
    private var _binding : FragmentHistoryBinding? = null

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * shop view model provider. Its getting shop view model object from the application context
         */
        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        /**
         * user view model provider. Its getting user view model object from the application context
         */
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        /**
         * user history view model provider. Its getting user history view model object
         * from the application context
         */
        userShoppingHistoryViewModel = ViewModelProvider(requireActivity())[HistoryViewModel::class.java]

        /**
         * Inflate the layout for this activity
         */
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        /**
         * getting user history from database
        */
        lifecycleScope.launch {
            val job = async {
                 return@async userShoppingHistoryViewModel.setUserShoppingHistory(
                     mShopViewModel.getUserShoppingHistory(userViewModel.getUserId())
                 )
            }
            job.await()

            // creating adapter
            val adapter = HistoryAdapter(userShoppingHistoryViewModel.getUserShoppingHistory().reversed(),requireContext())
            // setting layout
            binding.productsList.layoutManager = LinearLayoutManager(requireContext())
            // setting adapter
            binding.productsList.adapter = adapter

        }

        return binding.root

    }
}