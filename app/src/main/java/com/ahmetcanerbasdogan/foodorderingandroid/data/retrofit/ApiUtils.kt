package com.ahmetcanerbasdogan.foodorderingandroid.data.retrofit

import com.ahmetcanerbasdogan.foodorderingandroid.util.Constants.BASE_URL

class ApiUtils {

    companion object{
        fun getFoodService(): FoodService {
            return RetrofitClient.getClient(BASE_URL).create(FoodService::class.java)
        }
    }
}