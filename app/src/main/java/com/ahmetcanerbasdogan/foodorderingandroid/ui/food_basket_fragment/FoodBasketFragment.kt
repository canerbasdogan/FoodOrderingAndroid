package com.ahmetcanerbasdogan.foodorderingandroid.ui.food_basket_fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahmetcanerbasdogan.foodorderingandroid.R
import com.ahmetcanerbasdogan.foodorderingandroid.databinding.FragmentFoodBasketBinding
import com.ahmetcanerbasdogan.foodorderingandroid.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodBasketFragment : Fragment() {

    private lateinit var binding: FragmentFoodBasketBinding
    private lateinit var viewModel: FoodBasketViewModel
    private lateinit var sharedViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_basket, container, false)
        binding.foodBasketFragment = this

        viewModel.foodEntityList.observe(viewLifecycleOwner) {
            if (it != null) {
                val adapter = FoodBasketAdapter(requireContext(), it, viewModel)
                binding.foodBasketAdapter = adapter
                binding.basketTotal = viewModel.getTotalPrice()

            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: FoodBasketViewModel by viewModels()
        viewModel = tempViewModel
        val tempSharedViewModel: MainViewModel by activityViewModels()
        sharedViewModel = tempSharedViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.userName = sharedViewModel.userName
        viewModel.getUser()
    }

    fun buttonApproveOrder() {
        sharedViewModel.foodList.value = viewModel.foodEntityList.value
        viewModel.clearBasket()
        Toast.makeText(requireContext(), "Sipariş Verildi!", Toast.LENGTH_LONG).show()
        val action = FoodBasketFragmentDirections.actionFoodShoppingCartFragmentToFoodListFragment()
        this.findNavController().navigate(action)
    }

    fun buttonClearBasket() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Sepet Temizleme İşlemi")
        builder.setMessage("Sepetteki Bütün Ürünler Silinsin Mi?")

        builder.setPositiveButton("Evet") { _, _ ->
            viewModel.clearBasket()
            Toast.makeText(requireContext(), "Sepet Temizlendi!", Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("Hayır") { _, _ ->
            Toast.makeText(requireContext(), "İşlem Başarısız!", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }
}
