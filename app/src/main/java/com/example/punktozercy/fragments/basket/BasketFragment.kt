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
import com.example.punktozercy.SharedData
import com.example.punktozercy.databinding.FragmentBasketBinding
import com.example.punktozercy.fragments.basket.adapters.BasketAdapter
import com.example.punktozercy.model.ShoppingHistory
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * class responsible for basket fragment. It is used by the user to view previously selected
 * products, remove them, view price and cashback details, and purchase selected products
 * @property mShopViewModel
 * @property sharedData
 * @property _binding
 * @property basketViewModel
 * @property userViewModel
 * @property cashBack
 * @property binding
 */

class BasketFragment : Fragment() {

    /**
     * variable responsible for managing database data
     */
    private lateinit var mShopViewModel: ShopViewModel

    /**
     * an object of the class in which methods are needed to modify the list of products
     */
    val sharedData: SharedData = SharedData()

    /**
     * variable responsible for managing basket data
     */
    lateinit var basketViewModel: BasketViewModel

    /**
     * variable responsible for managing user data
     */
    private lateinit var userViewModel: UserViewModel

    /**
     * variable storing information about cashback calculated on the basis of products in the basket
     */
    private var cashBack: Int = 0

    /**
     * binding object
     */
    private var _binding: FragmentBasketBinding? = null

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
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * Inflate the layout for this activity
         */
        _binding = FragmentBasketBinding.inflate(inflater, container, false)

        /**
         * basket view model provider. Its getting basket view model object from the application
         * context
         */
        basketViewModel = ViewModelProvider(this)[BasketViewModel::class.java]

        /**
         * shop view model provider. Its getting shop view model object from the application context
         */
        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]

        /**
         * user view model provider. Its getting user view model object from the application context
         */
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        /**
         * 'buyUsingMoney' variable observer, when its value changes, information about the selected
         * payment method changes dynamically in the application
         */
        BasketViewModel.buyUsingMoney.observe(this) {

            var moneyPriceNotRounded = SharedData.textMoneyPrice.value
            var pointsPriceNotRounded = SharedData.textPointsPrice.value

            if (SharedData.textMoneyPrice.value!! < 0.01) {
                moneyPriceNotRounded = 0.0
            }

            if (SharedData.textPointsPrice.value!! < 0) {
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

        /**
         * a function that calculates the price of all products in the basket
         */
        if(SharedData.productList.size > 0 && !SharedData.firstProduct)
        {
            for(item in SharedData.productList){
                SharedData.textMoneyPrice.value = SharedData.textMoneyPrice.value?.plus(item.price)
                SharedData.textPointsPrice.value = SharedData.textPointsPrice.value?.plus(item.pointsPrice!!)
            }
        }else{
            SharedData.textMoneyPrice.value = 0.0
            SharedData.textPointsPrice.value = 0
        }

        /**
         * a watcher that calls the showMoney() method whenever the calculated price changes to
         * dynamically display it in the application
         */
        SharedData.textMoneyPrice.observe(this) {
            showMoney()
        }

        /**
         * a watcher that calls the showPoints() method whenever the calculated price changes to
         * dynamically display it in the application
         */
        SharedData.textPointsPrice.observe(this) {
           showPoints()
        }

        /**
         * an observer that dynamically prints an empty cart text to the screen if there are no
         * products in the cart
         */
        SharedData.basketText.observe(this) {
            binding.emptyBasketText.text = SharedData.basketText.value
        }

        /**
         * creating adapter with current list of products
         */
        val adapter = BasketAdapter(sharedData.getProductList(), requireContext())

        /**
         * setting adapter as linear
         */
        binding.basketRecycler.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        /**
         * adapter assignment
         */
        binding.basketRecycler.adapter = adapter

        /**
         * if there are no products in basket there is no need to display basketRecycler
         */
        binding.basketRecycler.isVisible = !SharedData.firstProduct

        /**
         * the listener of the switch responsible for changing the currency, it updates the data on
         * the screen regarding the selected currency
         */
        binding.typeOfCurrency.setOnCheckedChangeListener { compoundButton, b ->
            BasketViewModel.buyUsingMoney.value = binding.typeOfCurrency.isChecked
            if(BasketViewModel.buyUsingMoney.value == false){
                binding.typeOfCurrency.text = "Buy with points"
                println(binding.typeOfCurrency.isChecked)
                cashBack = (SharedData.textPointsPrice.value!! * 0.1).toInt()
                binding.deliveryPrice.text = "Delivery with points: 100"
                showPoints()
            }else{
                binding.typeOfCurrency.text = "Buy with money"
                cashBack = (SharedData.textMoneyPrice.value!! * 0.3).toInt()
                println(SharedData.textMoneyPrice.value!!)
                println(cashBack)
                binding.deliveryPrice.text = "Delivery with money: 10zł"
                showMoney()
            }
        }

        /**
         * button 'BUY PRODUCTS' listener. It calculates the cashback for the user for the
         * purchased products and clears the basket. He is also responsible for checking whether
         * the user has enough points and informing him if he has too few
         */
        binding.checkOut.setOnClickListener {
            if(BasketViewModel.buyUsingMoney.value!!){
                cashBack = (SharedData.textMoneyPrice.value!! * 0.3).toInt()
                updateUserPoints(cashBack,userViewModel.getPoints())
                buyProducts(true)
                Toast.makeText(requireContext(), "You earned $cashBack points as reward", Toast.LENGTH_SHORT).show()
                clearBasket(adapter)
            }
            else
            {
                if(userViewModel.getPoints() > SharedData.textPointsPrice.value!!){
                    cashBack = (SharedData.textPointsPrice.value!! * 0.1).toInt()
                    val pointsAfterShopping = userViewModel.getPoints() - SharedData.textPointsPrice.value!!
                    updateUserPoints(cashBack,pointsAfterShopping)
                    buyProducts(false)
                    Toast.makeText(requireContext(), "You earned $cashBack points as reward", Toast.LENGTH_SHORT).show()
                    clearBasket(adapter)

                }else{
                    Toast.makeText(requireContext(), "You do not have enough points", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    /**
     * the function is called every time the price of products in money changes. It formats the
     * text appropriately and displays it on the screen
     */
    private fun showMoney(){
        var moneyPriceNotRounded = SharedData.textMoneyPrice.value

        if(SharedData.textMoneyPrice.value!! < 0.01) {
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

    /**
     * the function is called every time the price of products in points changes. It formats the
     * text appropriately and displays it on the screen
     */
    private fun showPoints(){
        var pointsPriceNotRounded = SharedData.textPointsPrice.value


        if(SharedData.textPointsPrice.value!! < 0.01){
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

    /**
     * function called when purchasing products. It updates the history of the user's purchased
     * products
     * @param typeOfCurrency true means that currency is money, false means points
     */
    @SuppressLint("SimpleDateFormat")
    private fun buyProducts(typeOfCurrency:Boolean){
        lifecycleScope.launch {
            val job = async{
                for(products in sharedData.getProductList()){
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

    /**
     * function called to clear the cart. It resets all values and sets the inscription
     * about the empty basket
     * @param adapter adapter responsible for showing products in basket
     */
    private fun clearBasket(adapter: BasketAdapter){
        sharedData.clearProductList()
        adapter.notifyDataSetChanged()
        SharedData.basketText.value = "YOU HAVE EMPTY BASKET"
        SharedData.textMoneyPrice.value = 0.0
        SharedData.textPointsPrice.value = 0
        SharedData.amountOfProductsInBasket.value = 0
    }

    /**
     * function responsible for updating user points in database
     * @param cashBack amount of calculated cashback
     * @param pointsAfterShopping amount of user points with added cashback
     */
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