package com.ahmetcanerbasdogan.foodorderingandroid.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.FoodEntity
import com.ahmetcanerbasdogan.foodorderingandroid.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    var userName = MutableLiveData("ahmet_caner_basdogan")
    var basketCount = MutableLiveData<Int>()
    var basketTotalPrice = MutableLiveData<Int>()
    var foodList = MutableLiveData<List<FoodEntity>>()


    fun updateBasket(foodEntity: FoodEntity) {
        var quantity = 0
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foodRepository.getFood(userName.value).filter { it.foodName == foodEntity.foodName }.forEach {
                    quantity += it.foodQuantity
                    foodRepository.deleteFood(it.basketFoodId, userName.value)
                }

                foodRepository.addBasket(
                    foodEntity.foodName,
                    foodEntity.foodImageUrl,
                    foodEntity.foodPrice,
                    quantity,
                    userName.value!!
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getBasketCount() {
        var count = 0
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foodRepository.getFood(userName.value).forEach {
                    count += it.foodQuantity
                }
            } catch (e: Exception) {
                count = 0
            }
            basketCount.value = count
        }
    }

    fun getBasketTotalPrice() {
        var total = 0
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foodRepository.getFood(userName.value).forEach {
                    total += it.foodQuantity * it.foodPrice
                }
            } catch (e: Exception) {
                total = 0
            }
            basketTotalPrice.value = total
        }
    }
}