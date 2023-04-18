package com.example.punktozercy.fragments.basket

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punktozercy.R
import com.example.punktozercy.SelectedProducts
import com.example.punktozercy.databinding.FragmentBasketBinding
import com.example.punktozercy.fragments.basket.adapters.BasketAdapter
import com.example.punktozercy.model.ShoppingHistory
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

class BasketFragment : Fragment() {
    private lateinit var mShopViewModel: ShopViewModel
    val selectedProducts: SelectedProducts = SelectedProducts()
    private var _binding: FragmentBasketBinding? = null
    lateinit var basketViewModel: BasketViewModel
    private lateinit var userViewModel: UserViewModel

    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)

        basketViewModel = ViewModelProvider(this)[BasketViewModel::class.java]
        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

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

        binding.checkOut.setOnClickListener {
            //TODO aktualizacja danych uzytkownika (punkty itp)
           lifecycleScope.launch {
             val job = async{
                 for(products in selectedProducts.getProductList()){
                     val time = Calendar.getInstance().time
                     val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                     val current = formatter.format(time).toString()
                     val history = ShoppingHistory(0,current.toString(),
                         userViewModel.getUserId(),products.productId)
                     mShopViewModel.addUserShoppingHistory(history)
             }

               }
               job.await()

           }
        }
//        binding.testButton.setOnClickListener {
//            Toast.makeText(requireContext(), selectedProducts.getTest(), Toast.LENGTH_SHORT).show()
//        }



        return binding.root
    }
}