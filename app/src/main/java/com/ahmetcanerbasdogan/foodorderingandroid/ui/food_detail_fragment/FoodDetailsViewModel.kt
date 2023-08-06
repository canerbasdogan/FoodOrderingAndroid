package com.ahmetcanerbasdogan.foodorderingandroid.ui.food_detail_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.FoodEntity
import com.ahmetcanerbasdogan.foodorderingandroid.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    val foodEntityList = MutableLiveData<List<FoodEntity>>()
    var userName = MutableLiveData<String>()
    lateinit var incomingFoodEntity: FoodEntity

    fun addFood(
        foodName: String,
        foodImageName: String,
        foodPrice: Int,
        foodQuantity: Int,
        userName: String
    ) {
        incomingFoodEntity =
            FoodEntity(1, foodName, foodImageName, foodPrice, foodQuantity, userName)

        viewModelScope.launch {
            foodRepository.addBasket(foodName, foodImageName, foodPrice, foodQuantity, userName)
            loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                foodEntityList.value = foodRepository.getFood(userName.value)
            } catch (e: Exception) {
                foodEntityList.value = emptyList()
            }
        }
    }

    fun buttonIncrease(quantity: String): Int {
        return if (quantity.isNotBlank()) {
            quantity.toInt() + 1
        } else {
            1
        }
    }

    fun buttonDecrease(quantity: String): Int {
        if (quantity.toInt() > 1) {
            quantity.toInt() - 1
        }
        return quantity.toInt() - 1
    }

    fun checkQuantity(quantity: String): String {
        return if (quantity.isNotBlank()) {
            if (quantity.toInt() < 1) {
                "1"
            } else {
                quantity
            }
        } else {
            ""
        }
    }

    fun updateTotalPrice(quantity: String, price: Int): Int {
        return if (quantity.isNotBlank()) {
            quantity.toInt() * price
        } else {
            price
        }
    }
}