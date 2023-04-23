package com.example.punktozercy.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.punktozercy.databinding.FragmentSendPointsBinding
import com.example.punktozercy.viewModel.ShopViewModel
import com.example.punktozercy.viewModel.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SendPointsFragment : Fragment() {
    private var _binding: FragmentSendPointsBinding? = null
    private lateinit var mShopViewModel: ShopViewModel
    private lateinit var userViewModel: UserViewModel
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSendPointsBinding.inflate(inflater,container,false)

        //shop model provider
        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        //user model provider
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        //init points value using user model attribute
        loadData()

        binding.sendPoints.setOnClickListener {
            lifecycleScope.launch {
                if(checkInputs()){
                    val senderUserPoints :Int = userViewModel.getPoints() - binding.amountPoints.text.toString().toInt()

                    //update sender user points
                    val job = lifecycleScope.async{
                        mShopViewModel.updateUserPoints(userViewModel.getUser().userId,senderUserPoints)

                        //update receiver user points
                        mShopViewModel.updateReceiverUserPoints(binding.TextEmailAddress.text.toString(),
                            binding.amountPoints.text.toString().toInt())
                    }
                    job.await()
                    val job2 = lifecycleScope.async {
                        while (true){
                            // set actual user after update
                            userViewModel.setUser(mShopViewModel.getUserById(userViewModel.getUser().userId))
                            if(senderUserPoints == userViewModel.getPoints()){
                                return@async true
                            }
                        }
                    }
                    job2.await()
                    findNavController().navigateUp()
                }
            }
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.amountPoints.addTextChangedListener(
            afterTextChanged ={
                if (!it.isNullOrBlank() && it[0] == '0') {
                    it.delete(0, 1)
                }
            }
        )
        return binding.root
    }


    private fun loadData(){
        binding.points.text = userViewModel.getPoints().toString()
    }

  private suspend fun checkEmailInput():Boolean{
        val email:String = binding.TextEmailAddress.text.toString()
        if(email.isNotEmpty()){
            val job = lifecycleScope.async {
                if(mShopViewModel.isUserEmailExists(email)){
                    if(email == userViewModel.getEmail()){
                        binding.TextEmailAddress.error = "You cannot send points to yourself"
                        return@async false
                    }else{
                        return@async true
                    }
                }
                else{
                    binding.TextEmailAddress.error = "Email address is invalid"
                    return@async false
                }
            }
            return job.await()
        }
      else{
            binding.TextEmailAddress.error = "Email address cannot be empty"
            return false
        }
    }

    private  fun checkPointsInput():Boolean{

        if(binding.amountPoints.text.toString().isEmpty()){
            binding.amountPoints.error = "Number of points cannot be empty"
            return false
        }
        val points :Int = binding.amountPoints.text.toString().toInt()

        return if(points > userViewModel.getPoints()){
            binding.amountPoints.error = "You do not have enough points"
            false
        }else{
            true
        }
    }

    private suspend fun checkInputs():Boolean{
        val job1 = lifecycleScope.async {
            val job = lifecycleScope.async {
                checkEmailInput()
                checkPointsInput()
                return@async(checkEmailInput() && checkPointsInput())
            }
            return@async job.await()
        }
        return job1.await()



    }

}

