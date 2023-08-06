package com.ahmetcanerbasdogan.foodorderingandroid.data.retrofit

import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.FoodEntityItem
import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.FoodsItem
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodService {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun loadFoods(): FoodsItem

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToBasket(
        @Field("yemek_adi") foodName: String?,
        @Field("yemek_resim_adi") foodImageUrl: String?,
        @Field("yemek_fiyat") foodPrice: Int?,
        @Field("yemek_siparis_adet") foodQuantity: Int?,
        @Field("kullanici_adi") userName: String?
    ): FoodEntityItem

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun loadBasket(
        @Field("kullanici_adi") userName: String?
    ): FoodEntityItem

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteFromBasket(
        @Field("sepet_yemek_id") basketFoodId: Int?,
        @Field("kullanici_adi") userName: String?
    ): FoodEntityItem
}