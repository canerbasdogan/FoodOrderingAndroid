package com.ahmetcanerbasdogan.foodorderingandroid.ui.home_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.Foods
import com.ahmetcanerbasdogan.foodorderingandroid.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    val foodList = MutableLiveData<List<Foods>>()
    private val sourceFoodList = MutableLiveData<List<Foods>>()
    private val selectionFoodList = MutableLiveData<List<Foods>>()

    init {
        loadFoods()
    }

    private fun loadFoods() {
        viewModelScope.launch {
            foodList.value = foodRepository.loadFoods()
            sourceFoodList.value = foodList.value
            selectionFoodList.value = foodList.value
        }
    }
}