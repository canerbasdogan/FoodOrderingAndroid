package com.ahmetcanerbasdogan.foodorderingandroid.ui.food_detail_fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ahmetcanerbasdogan.foodorderingandroid.R
import com.ahmetcanerbasdogan.foodorderingandroid.databinding.FragmentFoodDetailsBinding
import com.bumptech.glide.Glide
import com.ahmetcanerbasdogan.foodorderingandroid.ui.main.MainViewModel
import com.ahmetcanerbasdogan.foodorderingandroid.util.Constants.BASE_URL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFoodDetailsBinding
    private lateinit var viewModel: FoodDetailsViewModel
    private lateinit var sharedViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_details, container, false)
        binding.foodDetailsFragment = this
        viewModel.userName = sharedViewModel.userName

        viewModel.foodEntityList.observe(viewLifecycleOwner){
            if(it != null){
                sharedViewModel.updateBasket(viewModel.incomingFoodEntity)
            }
        }

        loadDetails()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FoodDetailsViewModel by viewModels()
        viewModel = tempViewModel
        val tempSharedViewModel: MainViewModel by activityViewModels()
        sharedViewModel = tempSharedViewModel
    }

    private fun loadDetails(){
        val bundle: FoodDetailsFragmentArgs by navArgs()
        val foodItem = bundle.food
        binding.foodData = foodItem

        binding.quantityOfFood = "1"
        binding.totalPrice = foodItem.foodPrice.toString()

        val url = "$BASE_URL/yemekler/resimler/${foodItem.foodImageUrl}"
        Glide.with(binding.root).load(url).override(800, 800).into(binding.ivDetailsImage)
    }

    fun backPressButton(){
        Navigation.findNavController(requireView()).popBackStack()
    }

    fun buttonAddToCart(foodName: String, foodImageName: String, foodPrice: Int, foodQuantity: Int) {
        viewModel.addFood(foodName, foodImageName, foodPrice, foodQuantity, sharedViewModel.userName.value.toString())

        val builder = AlertDialog.Builder(requireView().context)
        builder.setTitle("İşlem Başarılı")
        builder.setMessage("$foodQuantity Adet $foodName Sepete Eklendi")

        builder.setPositiveButton("Sepete Git") { _, _ ->
            val nav = FoodDetailsFragmentDirections.detailsToCart()
            Navigation.findNavController(requireView()).navigate(nav)
        }

        builder.setNegativeButton("Ana Sayfaya Git") { _, _ ->
            Navigation.findNavController(requireView()).popBackStack()
        }
        builder.show()
    }

    fun buttonIncrease(quantity: String){
        binding.quantityOfFood = viewModel.buttonIncrease(quantity).toString()
    }

    fun buttonDecrease(quantity: String){
        binding.quantityOfFood = viewModel.buttonDecrease(quantity).toString()
    }

    fun checkQuantity(quantity: String, price: Int){
        binding.quantityOfFood = viewModel.checkQuantity(quantity)
        binding.totalPrice = viewModel.updateTotalPrice(quantity, price).toString()
    }
}