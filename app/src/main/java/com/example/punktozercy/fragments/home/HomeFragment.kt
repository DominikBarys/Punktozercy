package com.example.punktozercy.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punktozercy.databinding.FragmentHomeBinding
import com.example.punktozercy.fragments.home.adapters.Contact
import com.example.punktozercy.fragments.home.adapters.HorizontalAdapter
import com.example.punktozercy.mainscreen.MainScreenViewModel
import com.example.punktozercy.viewModel.UserViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
  //  private var mainScreenVm = activity?.let { ViewModelProvider(it)[MainScreenViewModel::class.java] }
   private lateinit var mainScreenVm: MainScreenViewModel
    private lateinit var userViewModel: UserViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        mainScreenVm = ViewModelProvider(requireActivity())[MainScreenViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }


        binding.debugAddProductButton.setOnClickListener{
            mainScreenVm.amountOfProductsInBasket.value = mainScreenVm.amountOfProductsInBasket.value!! + 1
        }


        val adapter = HorizontalAdapter(createContacts())

        binding.recyclerViewHorizontal.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.recyclerViewHorizontal.adapter = adapter






        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun createContacts(): List<Contact> = buildList{
        for(i in 0 .. 500){
            val newContact = Contact("$i", "$i")
            add(newContact)
        }
    }


}