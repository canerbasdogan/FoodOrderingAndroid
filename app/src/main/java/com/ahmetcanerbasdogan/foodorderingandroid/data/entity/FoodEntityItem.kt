package com.ahmetcanerbasdogan.foodorderingandroid.data.entity

import com.google.gson.annotations.SerializedName

data class FoodEntityItem(
    @SerializedName("sepet_yemekler")
    val foodEntities: List<FoodEntity>,
    val success: Int
)