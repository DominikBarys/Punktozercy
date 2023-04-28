package com.example.punktozercy.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punktozercy.databinding.FragmentHomeBinding
import com.example.punktozercy.fragments.home.adapters.Offer
import com.example.punktozercy.fragments.home.adapters.OfferAdapter
import com.example.punktozercy.viewModel.UserViewModel
import com.example.punktozercy.R
import com.example.punktozercy.fragments.home.productslist.ProductViewModel

/**
 * class responsible for home fragment. It is used to display the user's name, their number of
 * points and a list of available offers that can be filtered
 * @property userViewModel
 * @property homeViewModel
 * @property productViewModel
 * @property _binding
 * @property binding
 * @property onCreateView
 * @property onDestroyView
 * @property createOffers
 */
class HomeFragment : Fragment() {

    /**
     * variable responsible for managing user data
     */
    private lateinit var userViewModel: UserViewModel

    /**
     * variable responsible for managing home panel data
     */
    private  lateinit var homeViewModel: HomeViewModel

    /**
     * variable responsible for managing products' data
     */
    private lateinit var productViewModel: ProductViewModel

    /**
     * binding object
     */
    private var _binding: FragmentHomeBinding? = null

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
         * Inflate the layout for this activity
         */
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        /**
         * home view model provider. Its getting home view model object from the application
         * context
         */
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        /**
         * product view model provider. Its getting product view model object from the application
         * context
         */
        productViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]

        /**
         * user view model provider. Its getting user view model object from the application
         * context
         */
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        /**
         * creating adapter with current list of offers
         */
        val adapter = OfferAdapter(createOffers())

        /**
         * setting adapter as linear
         */
        binding.recyclerViewHorizontal.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        /**
         * adapter assignment
         */
        binding.recyclerViewHorizontal.adapter = adapter

        createOffers()

        /**
         * Listener responsible for filtering the list of offers. Creates a new offer list
         * containing food only
         */
        binding.cardViewFood.setOnClickListener {
            binding.allImageView.setImageResource(R.drawable.filter_all)
            binding.foodImageView.setImageResource(R.drawable.filter_food_selected)
            binding.drinkImageView.setImageResource(R.drawable.filter_drink)
            binding.otherImageView.setImageResource(R.drawable.filter_other)
            val filteredList = ArrayList<Offer>()
            for(i in homeViewModel.offerArray){
                if(i.third.contains("ready meals") || i.third.contains("fruits") || i.third.contains("bakery") ||
                    i.third.contains("dairy") || i.third.contains("meat") || i.third.contains("vegetables"))
                    filteredList.add(Offer(i.first, i.second, i.third))
            }
            adapter.setFilteredList(filteredList)
        }

        /**
         * Listener responsible for filtering the list of offers. Creates a new offer list
         * containing drinks only
         */
        binding.cardViewDrinks.setOnClickListener {
            binding.allImageView.setImageResource(R.drawable.filter_all)
            binding.foodImageView.setImageResource(R.drawable.filter_food)
            binding.drinkImageView.setImageResource(R.drawable.filter_drink_selected)
            binding.otherImageView.setImageResource(R.drawable.filter_other)
            val filteredList = ArrayList<Offer>()
            for(i in homeViewModel.offerArray){
                if(i.third.contains("fizzy drink") || i.third.contains("drink") || i.third.contains("beverages"))
                    filteredList.add(Offer(i.first, i.second, i.third))
            }
            adapter.setFilteredList(filteredList)
        }

        /**
         * Listener responsible for filtering the list of offers. Creates a new offer list
         * containing 'other' offers only
         */
        binding.cardViewOther.setOnClickListener {
            binding.allImageView.setImageResource(R.drawable.filter_all)
            binding.foodImageView.setImageResource(R.drawable.filter_food)
            binding.drinkImageView.setImageResource(R.drawable.filter_drink)
            binding.otherImageView.setImageResource(R.drawable.filter_other_selected)
            val filteredList = ArrayList<Offer>()
            for(i in homeViewModel.offerArray){
                if(i.third.contains("sweets") || i.third.contains("spices"))
                    filteredList.add(Offer(i.first, i.second, i.third))
            }
            adapter.setFilteredList(filteredList)
        }

        /**
         * Listener responsible for filtering the list of offers. Creates a new offer list
         * containing all offers
         */
        binding.cardViewNone.setOnClickListener {
            binding.allImageView.setImageResource(R.drawable.filter_all_selected)
            binding.foodImageView.setImageResource(R.drawable.filter_food)
            binding.drinkImageView.setImageResource(R.drawable.filter_drink)
            binding.otherImageView.setImageResource(R.drawable.filter_other)
            val filteredList = ArrayList<Offer>()
            for(i in homeViewModel.offerArray){
                if(i.third.contains(""))
                    filteredList.add(Offer(i.first, i.second, i.third))
            }
            adapter.setFilteredList(filteredList)
        }

        /**
         * the function redirects the user to the panel with products from the selected offer
         */
        adapter.onItemClick = {
            productViewModel.setCategory(it.category)
            findNavController().navigate(R.id.action_navigation_home_to_productsListFragment)
        }

        /**
         * displaying username in home panel
         */
        var homeUserInfo = homeViewModel.HELLO_USER + userViewModel.getUsername()
        binding.helloUser.text = homeUserInfo

        /**
         * displaying user points in home panel
         */
        homeUserInfo = homeViewModel.POINTS_STRING_PREFIX + userViewModel.getPoints().toString() + homeViewModel.POINTS_STRING_SUFIX
        binding.pointsText.text = homeUserInfo

        return binding.root
    }

    /**
     * class destructor
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * function that creates a list of offers
     */
    private fun createOffers(): List<Offer> = buildList{

        homeViewModel.offerArray.forEachIndexed{ _, triple ->
            val(id,desc,cat) = triple
            val newOffer = Offer(id,desc,cat)
            add(newOffer)
        }
    }
}