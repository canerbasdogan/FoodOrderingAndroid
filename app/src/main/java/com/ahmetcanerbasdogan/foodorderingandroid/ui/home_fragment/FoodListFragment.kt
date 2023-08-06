package com.ahmetcanerbasdogan.foodorderingandroid.ui.home_fragment

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ahmetcanerbasdogan.foodorderingandroid.R
import com.ahmetcanerbasdogan.foodorderingandroid.databinding.FragmentFoodListBinding
import com.ahmetcanerbasdogan.foodorderingandroid.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding
    private lateinit var viewModel: FoodListViewModel
    private lateinit var sharedViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_list, container, false)
        binding.foodListFragment = this
        binding.userName = sharedViewModel.userName.value.toString()

        viewModel.foodList.observe(viewLifecycleOwner) {
            if (it != null) {
                val adapter = FoodListAdapter(requireContext(), it)
                binding.foodListAdapter = adapter
            }
        }

        sharedViewModel.basketCount.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.basketCount = sharedViewModel.basketCount.value.toString()
            } else {
                binding.basketCount  = "0"
            }
        }

        sharedViewModel.basketTotalPrice.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.basketTotal = sharedViewModel.basketTotalPrice.value.toString()
            } else {
                binding.basketTotal = "0"
            }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FoodListViewModel by viewModels()
        viewModel = tempViewModel

        val tempSharedViewModel: MainViewModel by activityViewModels()
        sharedViewModel = tempSharedViewModel
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.getBasketCount()
        sharedViewModel.getBasketTotalPrice()
    }
}