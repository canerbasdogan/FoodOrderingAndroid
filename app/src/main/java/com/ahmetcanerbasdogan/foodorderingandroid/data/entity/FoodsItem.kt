package com.ahmetcanerbasdogan.foodorderingandroid.data.entity

import com.google.gson.annotations.SerializedName

data class FoodsItem(
    @SerializedName("yemekler")
    val foods: List<Foods>,
    val success: Int
)