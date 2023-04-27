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

/**
 * class that is responsible for send points fragment. It is allowing user to send points to another
 * user by providing he is email and amount of points he want to send
 * @property _binding
 * @property mShopViewModel
 * @property userViewModel
 * @property binding
 * @property onCreateView
 * @property loadData
 * @property sendPointsToUser
 * @property checkEmailInput
 * @property checkPointsInput
 * @property checkInputs
 */
class SendPointsFragment : Fragment() {
    /**
     * binding object
     */
    private var _binding: FragmentSendPointsBinding? = null
    /**
     * variable responsible for managing database data
     */
    private lateinit var mShopViewModel: ShopViewModel
    /**
     * user view model provider. Its getting user view model object from the application context
     */
    private lateinit var userViewModel: UserViewModel
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
         * Inflate the layout for this fragment
         */
        _binding = FragmentSendPointsBinding.inflate(inflater,container,false)

        /**
         * shop view model provider. Its getting shop view model object from the application context
         */
        mShopViewModel = ViewModelProvider(requireActivity())[ShopViewModel::class.java]
        /**
         * user view model provider. Its getting user view model object from the application context
         */
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]

        /**
         * setting text labels from data user
         */
        loadData()


        /**
         * send points to another user and update points after button is clicked
         */
        binding.sendPoints.setOnClickListener {
            sendPointsToUser()
        }

        /**
         * back to settings fragment after button is clicked
         */
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        /**
         * prevent user from sending 0 points to another user
         */
        binding.amountPoints.addTextChangedListener(
            afterTextChanged ={
                if (!it.isNullOrBlank() && it[0] == '0') {
                    it.delete(0, 1)
                }
            }
        )
        return binding.root
    }


    /**
     * function that is responsible for setting text on the screen from user data
     */
    private fun loadData(){
        binding.points.text = userViewModel.getPoints().toString()
    }

    /**
     * function that is responsible for sending points to another user. It is updating sender points
     * in database and user view model and also updating receiver points in database
     */
    private fun sendPointsToUser(){
        lifecycleScope.launch {
            if(checkInputs()){
                //sender user points after send
                val senderUserPoints :Int = userViewModel.getPoints() - binding.amountPoints.text.toString().toInt()

                //update sender user points in database
                val job = lifecycleScope.async{
                    mShopViewModel.updateUserPoints(userViewModel.getUser().userId,senderUserPoints)

                    //update receiver user points in database
                    mShopViewModel.updateReceiverUserPoints(binding.TextEmailAddress.text.toString(),
                        binding.amountPoints.text.toString().toInt())
                }
                job.await()
                // update user view model
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
                //back to settings fragment
                findNavController().navigateUp()
            }
        }
    }

    /**
     * function that is responsible for checking email input. It checks if the receiver user email
     * address exists and database and prevents sending points to yourself
     * @return true if the receiver email exists in database, otherwise false if the user do not
     * exists or it is his own email and sending errors to him
     */
  private suspend fun checkEmailInput():Boolean{
        //get email from text
        val email:String = binding.TextEmailAddress.text.toString()
        if(email.isNotEmpty()){
            //check if the user exists
            val job = lifecycleScope.async {
                if(mShopViewModel.isUserEmailExists(email)){
                    //check if it is his own email
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

    /**
     * function that is responsible for checking points input.
     * @return true if the user have enough points to send, otherwise false
     */
    private  fun checkPointsInput():Boolean{

        //check if input is empty
        if(binding.amountPoints.text.toString().isEmpty()){
            binding.amountPoints.error = "Number of points cannot be empty"
            return false
        }
        val points :Int = binding.amountPoints.text.toString().toInt()

        // check if the user have enough points
        return if(points > userViewModel.getPoints()){
            binding.amountPoints.error = "You do not have enough points"
            false
        }else{
            true
        }
    }

    /**
     * function that checks if the user inputs (email, points) are correct
     * @return true if the inputs are valid, otherwise false
     */
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

