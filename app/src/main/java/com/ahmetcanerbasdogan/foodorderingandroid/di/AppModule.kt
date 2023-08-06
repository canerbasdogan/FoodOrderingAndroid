package com.ahmetcanerbasdogan.foodorderingandroid.di

import com.ahmetcanerbasdogan.foodorderingandroid.data.datasource.FoodDataSource
import com.ahmetcanerbasdogan.foodorderingandroid.data.repository.FoodRepository
import com.ahmetcanerbasdogan.foodorderingandroid.data.retrofit.ApiUtils
import com.ahmetcanerbasdogan.foodorderingandroid.data.retrofit.FoodService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFoodDataSource(foodService: FoodService) : FoodDataSource {
        return FoodDataSource(foodService)
    }

    @Provides
    @Singleton
    fun provideFoodRepository(foodDataSource: FoodDataSource) : FoodRepository {
        return FoodRepository(foodDataSource)
    }

    @Provides
    @Singleton
    fun provideFoodService() : FoodService {
        return ApiUtils.getFoodService()
    }
}