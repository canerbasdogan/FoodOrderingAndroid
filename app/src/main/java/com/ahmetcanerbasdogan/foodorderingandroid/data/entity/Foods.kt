package com.ahmetcanerbasdogan.foodorderingandroid.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Foods(
    @SerializedName("yemek_id")
    val foodId: Int,
    @SerializedName("yemek_adi")
    val foodName: String,
    @SerializedName("yemek_resim_adi")
    val foodImageUrl: String,
    @SerializedName("yemek_fiyat")
    val foodPrice: Int
) : Serializable