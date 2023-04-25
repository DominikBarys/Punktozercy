package com.example.punktozercy.fragments.basket

import android.annotation.SuppressLint
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
import com.example.punktozercy.SelectedProducts
import com.example.punktozercy.databinding.FragmentBasketBinding
import com.example.punktozercy.fragments.basket.adapters.BasketAdapter
import com.example.punktozercy.model.ShoppingHistory
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class BasketFragment : Fragment() {

    private lateinit var mShopViewModel: ShopViewModel
    val selectedProducts: SelectedProducts = SelectedProducts()
    private var _binding: FragmentBasketBinding? = null
    lateinit var basketViewModel: BasketViewModel
    private lateinit var userViewModel: UserViewModel
    private var cashBack: Int = 0
    private val binding get() = _binding!!

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)

        basketViewModel = ViewModelProvider(this)[BasketViewModel::class.java]
        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]



        BasketViewModel.buyUsingMoney.observe(this) {

            var moneyPriceNotRounded = SelectedProducts.textMoneyPrice.value
            var pointsPriceNotRounded = SelectedProducts.textPointsPrice.value

            if (SelectedProducts.textMoneyPrice.value!! < 0.01) {
                moneyPriceNotRounded = 0.0
            }

            if (SelectedProducts.textPointsPrice.value!! < 0) {
                pointsPriceNotRounded = 0
            }

            if (BasketViewModel.buyUsingMoney.value == true) {
                val formattedPrice = String.format("%.2f", moneyPriceNotRounded)
                binding.moneyPriceTextView.text = "Price in cash: $formattedPrice zł"
            }
            else {
                binding.moneyPriceTextView.text = "Price in points: $pointsPriceNotRounded "
            }
        }


        if(SelectedProducts.productList.size > 0 && !SelectedProducts.firstProduct)
        {
            SelectedProducts.textMoneyPrice.value = 0.0
            SelectedProducts.textPointsPrice.value = 0
            for(item in SelectedProducts.productList){
                SelectedProducts.textMoneyPrice.value = SelectedProducts.textMoneyPrice.value?.plus(item.price)
                SelectedProducts.textPointsPrice.value = SelectedProducts.textPointsPrice.value?.plus(item.pointsPrice!!)
            }
        }else{
            SelectedProducts.textMoneyPrice.value = 0.0
            SelectedProducts.textPointsPrice.value = 0
        }


        //TODO
        SelectedProducts.textMoneyPrice.observe(this) {
            showMoney()
        }

        SelectedProducts.textPointsPrice.observe(this) {
           showPoints()
        }



        SelectedProducts.basketText.observe(this) {
            binding.emptyBasketText.text = SelectedProducts.basketText.value
        }

        val adapter = BasketAdapter(selectedProducts.getProductList(), requireContext())
        binding.basketRecycler.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.basketRecycler.adapter = adapter


        binding.basketRecycler.isVisible = !SelectedProducts.firstProduct

        binding.typeOfCurrency.setOnCheckedChangeListener { compoundButton, b ->
            BasketViewModel.buyUsingMoney.value = binding.typeOfCurrency.isChecked
            if(BasketViewModel.buyUsingMoney.value == false){
                binding.typeOfCurrency.text = "Buy with points"
                println(binding.typeOfCurrency.isChecked)
                cashBack = (SelectedProducts.textPointsPrice.value!! * 0.1).toInt()
                binding.deliveryPrice.text = "Delivery with points: 100"
                showPoints()
            }else{
                binding.typeOfCurrency.text = "Buy with money"
                cashBack = (SelectedProducts.textMoneyPrice.value!! * 0.3).toInt()
                println(SelectedProducts.textMoneyPrice.value!!)
                println(cashBack)
                binding.deliveryPrice.text = "Delivery with money: 10zł"
                showMoney()
            }
        }

        binding.checkOut.setOnClickListener {
            //TODO aktualizacja danych uzytkownika (punkty itp)
            if(BasketViewModel.buyUsingMoney.value!!){

                cashBack = (SelectedProducts.textMoneyPrice.value!! * 0.3).toInt()
                updateUserPoints(cashBack,userViewModel.getPoints())
                buyProducts(true)
                Toast.makeText(requireContext(), "You earned $cashBack points as reward", Toast.LENGTH_SHORT).show()
                clearBucket(adapter)
            }
            else
            {
                if(userViewModel.getPoints() > SelectedProducts.textPointsPrice.value!!){

                    //TODO CASHABCK PO ZAKUPIE
                    cashBack = (SelectedProducts.textPointsPrice.value!! * 0.1).toInt()
                    val pointsAfterShopping = userViewModel.getPoints() - SelectedProducts.textPointsPrice.value!!
                    updateUserPoints(cashBack,pointsAfterShopping)
                    buyProducts(false)
                    Toast.makeText(requireContext(), "You earned $cashBack points as reward", Toast.LENGTH_SHORT).show()
                    clearBucket(adapter)

                }else{
                    Toast.makeText(requireContext(), "You do not have enough points", Toast.LENGTH_SHORT).show()
                }
            }

        }
        return binding.root

    }

    private fun showMoney(){
        var moneyPriceNotRounded = SelectedProducts.textMoneyPrice.value

        if(SelectedProducts.textMoneyPrice.value!! < 0.01) {
            moneyPriceNotRounded = 0.0
        }

        if(BasketViewModel.buyUsingMoney.value == true){
            var formattedPrice = String.format("%.2f", moneyPriceNotRounded)
            binding.moneyPriceTextView.text = "Price in cash: $formattedPrice zł"
            if(moneyPriceNotRounded!! > 0){
                val deliveryPrice = moneyPriceNotRounded!! + 10
                formattedPrice = String.format("%.2f", deliveryPrice)
                binding.deliveryText.text = "With delivery: $formattedPrice zł"
            }else{
                binding.deliveryText.text = "With delivery: 0 zł"
            }

        }
    }

    private fun showPoints(){
        var pointsPriceNotRounded = SelectedProducts.textPointsPrice.value


        if(SelectedProducts.textPointsPrice.value!! < 0.01){
            pointsPriceNotRounded = 0
        }

        if(BasketViewModel.buyUsingMoney.value == false){
            binding.moneyPriceTextView.text = "Price in points: $pointsPriceNotRounded"
            if(pointsPriceNotRounded!! > 0){
                val pointsWithDelivery = pointsPriceNotRounded + 100
                binding.deliveryText.text = "With delivery: $pointsWithDelivery points"
            }else{
                binding.deliveryText.text = "With delivery: 0 zł"
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun buyProducts(typeOfCurrency:Boolean){
        lifecycleScope.launch {
            val job = async{
                for(products in selectedProducts.getProductList()){
                    val time = Calendar.getInstance().time
                    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                    val current = formatter.format(time).toString()
                    val history = ShoppingHistory(0,current,
                        userViewModel.getUserId(),products.productId,typeOfCurrency)
                    mShopViewModel.addUserShoppingHistory(history)
                }

            }
            job.await()

        }
    }

    private fun clearBucket(adapter: BasketAdapter){
        selectedProducts.clearProductList()
        adapter.notifyDataSetChanged()
        SelectedProducts.basketText.value = "YOU HAVE EMPTY BASKET"
        SelectedProducts.textMoneyPrice.value = 0.0
        SelectedProducts.textPointsPrice.value = 0
        SelectedProducts.amountOfProductsInBasket.value = 0
    }

    private fun updateUserPoints(cashBack:Int,pointsAfterShopping:Int ){
        lifecycleScope.launch {
            val job = lifecycleScope.async {
                mShopViewModel.updateUserPoints(userViewModel.getUserId(),pointsAfterShopping + cashBack)

            }
            job.await()
            val job2 = lifecycleScope.async {
                while(true){
                    userViewModel.setUser(mShopViewModel.getUserById(userViewModel.getUserId()))
                    if(pointsAfterShopping + cashBack == userViewModel.getPoints()){
                        return@async true
                    }
                }

            }
            job2.await()
        }
    }
}