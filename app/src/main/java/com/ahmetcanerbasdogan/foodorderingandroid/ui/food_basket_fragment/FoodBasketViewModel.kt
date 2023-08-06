package com.ahmetcanerbasdogan.foodorderingandroid.ui.food_basket_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.FoodEntity
import com.ahmetcanerbasdogan.foodorderingandroid.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodBasketViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val sourceFoodEntityList = MutableLiveData<List<FoodEntity>>()
    var userName = MutableLiveData<String>()
    val foodEntityList = MutableLiveData<List<FoodEntity>>()

    fun getUser() {
        loadBasket(userName.value)
    }

    private fun loadBasket(userName: String?) {
        viewModelScope.launch {
            try {
                foodEntityList.value = foodRepository.getFood(userName)
            } catch (e: Exception) {
                foodEntityList.value = emptyList()
            }
            getTotalPrice()
            sourceFoodEntityList.value = foodEntityList.value
        }
    }

    fun deleteFood(foodId: Int?, userName: String?) {
        viewModelScope.launch {
            foodRepository.deleteFood(foodId, userName)
            loadBasket(userName)
        }
    }

    fun getTotalPrice(): String {
        var total = 0
        foodEntityList.value?.forEach {
            total += it.foodQuantity * it.foodPrice
        }
        return total.toString()
    }

    fun clearBasket() {
        foodEntityList.value?.forEach {
            deleteFood(it.basketFoodId, userName.value)
        }
    }
}