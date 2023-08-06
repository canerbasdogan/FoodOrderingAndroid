package com.ahmetcanerbasdogan.foodorderingandroid.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FoodEntity(
    @SerializedName("sepet_yemek_id")
    val basketFoodId: Int,
    @SerializedName("yemek_adi")
    val foodName: String,
    @SerializedName("yemek_resim_adi")
    val foodImageUrl: String,
    @SerializedName("yemek_fiyat")
    val foodPrice: Int,
    @SerializedName("yemek_siparis_adet")
    val foodQuantity: Int,
    @SerializedName("kullanici_adi")
    val userName: String
) : Serializable
