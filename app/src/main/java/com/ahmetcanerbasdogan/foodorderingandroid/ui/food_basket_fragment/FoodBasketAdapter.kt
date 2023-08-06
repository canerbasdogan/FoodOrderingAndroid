package com.ahmetcanerbasdogan.foodorderingandroid.ui.food_basket_fragment

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ahmetcanerbasdogan.foodorderingandroid.R
import com.bumptech.glide.Glide
import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.FoodEntity
import com.ahmetcanerbasdogan.foodorderingandroid.data.entity.Foods
import com.ahmetcanerbasdogan.foodorderingandroid.databinding.RowBasketBinding
import com.ahmetcanerbasdogan.foodorderingandroid.util.Constants.BASE_URL

class FoodBasketAdapter(
    private val adapterContext: Context,
    private val foodEntityList: List<FoodEntity>,
    private val viewModel: FoodBasketViewModel
) : RecyclerView.Adapter<FoodBasketAdapter.ViewHolder>() {

    class ViewHolder(binding: RowBasketBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: RowBasketBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowBasketBinding = DataBindingUtil.inflate(
            LayoutInflater.from(adapterContext),
            R.layout.row_basket,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val basket = foodEntityList[position]
        val bind = holder.binding
        bind.basketData = basket

        val food = Foods(0, basket.foodName, basket.foodImageUrl, basket.foodPrice)

        val url = "$BASE_URL/yemekler/resimler/${basket.foodImageUrl}"
        Glide.with(bind.basketFoodImageView).load(url).override(200, 200).into(bind.basketFoodImageView)

        bind.basketDeleteFoodButton.setOnClickListener {

            val builder = AlertDialog.Builder(adapterContext)
            builder.setTitle("Ürün Silme")
            builder.setMessage("${basket.foodQuantity} adet ${basket.foodName} silinsin mi?")

            builder.setPositiveButton("Evet") { _, _ ->
                viewModel.deleteFood(basket.basketFoodId, basket.userName)
                Toast.makeText(adapterContext,"Ürün Başarıyla Silindi!",Toast.LENGTH_SHORT).show()

            }
            builder.setNegativeButton("Hayır") { _, _ ->
                Toast.makeText(adapterContext,"Ürün Silinmedi!",Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }

        bind.basketRow.setOnClickListener {
            val action = FoodBasketFragmentDirections.cartToDetails(food)
            Navigation.findNavController(bind.root).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return foodEntityList.size
    }
}