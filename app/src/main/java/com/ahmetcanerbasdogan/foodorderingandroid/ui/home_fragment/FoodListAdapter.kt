package com.ahmetcanerbasdogan.foodorderingandroid.ui.home_fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmetcanerbasdogan.foodorderingandroid.R
import com.bumptech.glide.Glide
import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.Foods
import com.ahmetcanerbasdogan.foodorderingandroid.databinding.RowFoodsBinding
import com.ahmetcanerbasdogan.foodorderingandroid.util.Constants.BASE_URL

class FoodListAdapter(
    private val adapterContext: Context,
    private val foodList: List<Foods>
) : RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {

    class ViewHolder(binding: RowFoodsBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: RowFoodsBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowFoodsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(adapterContext),
            R.layout.row_foods,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        val bind = holder.binding
        bind.foodData = food

        val url = "$BASE_URL/yemekler/resimler/${food.foodImageUrl}"
        Glide.with(bind.ivListImage).load(url).override(350, 350).into(bind.ivListImage)

        bind.foodRowLinearLayot.setOnClickListener {
            val action = FoodListFragmentDirections.listToDetails(food)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}