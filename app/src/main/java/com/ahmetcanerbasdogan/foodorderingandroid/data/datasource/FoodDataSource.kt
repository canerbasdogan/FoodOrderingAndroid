package com.ahmetcanerbasdogan.foodorderingandroid.data.datasource

import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.FoodEntity
import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.Foods
import com.ahmetcanerbasdogan.foodorderingandroid.data.retrofit.FoodService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodDataSource(
    private val foodService: FoodService
) {

    suspend fun loadFoods(): List<Foods> =
        withContext(Dispatchers.IO) {
            foodService.loadFoods().foods
        }

    suspend fun addToBasket(
        foodName: String,
        foodImageUrl: String,
        foodPrice: Int,
        foodQuantity: Int,
        userName: String
    ) =
        foodService.addToBasket(foodName, foodImageUrl, foodPrice, foodQuantity, userName)


    suspend fun loadBasket(userName: String?): List<FoodEntity> =
        withContext(Dispatchers.IO) {
            foodService.loadBasket(userName).foodEntities
        }

    suspend fun deleteFromBasket(foodID: Int?, userName: String?) =
        foodService.deleteFromBasket(foodID, userName)
}