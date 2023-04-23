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
import com.example.punktozercy.fragments.home.adapters.HorizontalAdapter
import com.example.punktozercy.mainscreen.MainScreenViewModel
import com.example.punktozercy.viewModel.UserViewModel
import com.example.punktozercy.R
import com.example.punktozercy.fragments.home.productslist.ProductViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
   private lateinit var mainScreenVm: MainScreenViewModel
    private lateinit var userViewModel: UserViewModel
    private  lateinit var homeViewModel: HomeViewModel
    private lateinit var productViewModel: ProductViewModel

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        productViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        mainScreenVm = ViewModelProvider(requireActivity())[MainScreenViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

//        val root: View = binding.root
//
//        binding.debugAddProductButton.setOnClickListener{
//
//        }

       val adapter = HorizontalAdapter(createOffers())

       binding.recyclerViewHorizontal.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.recyclerViewHorizontal.adapter = adapter

        createOffers()

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

        adapter.onItemClick = {
            productViewModel.setCategory(it.category)
            findNavController().navigate(R.id.action_navigation_home_to_productsListFragment)
        }

        var tmp = homeViewModel.HELLO_USER + userViewModel.getUsername()
        binding.helloUser.text = tmp

        tmp = homeViewModel.POINTS_STRING + userViewModel.getPoints().toString() + homeViewModel.POINTS_STRING2
        binding.pointsText.text = tmp


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createOffers(): List<Offer> = buildList{

        homeViewModel.offerArray.forEachIndexed{ _, triple ->
            val(id,desc,cat) = triple
            val newOffer = Offer(id,desc,cat)
            add(newOffer)
        }
    }
}