package com.example.punktozercy.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.punktozercy.databinding.FragmentDashboardBinding
import com.example.punktozercy.model.User
import com.example.punktozercy.viewModel.UserViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var userViewModel: UserViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        userViewModel = ViewModelProvider(requireActivity() )[UserViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        Toast.makeText(requireContext(),userViewModel.getUser().points.toString(),Toast.LENGTH_LONG).show()
     //   Toast.makeText(requireContext(),userViewModel.getUser().userName,Toast.LENGTH_LONG).show()


       // val user = bundle!!.get
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}