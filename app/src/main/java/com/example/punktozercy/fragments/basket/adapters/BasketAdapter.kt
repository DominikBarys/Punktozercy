package com.example.punktozercy.fragments.basket.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.punktozercy.SelectedProducts
import com.example.punktozercy.databinding.ViewBasketBinding
import com.example.punktozercy.model.Product

class BasketAdapter(private val products: List<Product>, private val context: Context) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    var selectedProducts = SelectedProducts()

    inner class BasketViewHolder(binding: ViewBasketBinding) :ViewHolder(binding.root){
        val productPicture = binding.basketProductPicture
        val productName = binding.basketProduktName
        val productDescription = binding.basketProductDescription
        val productMoneyValue = binding.basketProductMoneyValue
        val productPointsValue = binding.basketProductPointsValue
        val removeButton = binding.removeButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBasketBinding = ViewBasketBinding.inflate(inflater, parent, false)
        return BasketViewHolder(viewBasketBinding)
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.productName.text = products[position].name
        holder.productDescription.text = products[position].description
        holder.productMoneyValue.text = products[position].price.toString()
        holder.productPointsValue.text = products[position].pointsPrice.toString()
        showImage(holder.productPicture, products[position].imagePath!!)
        holder.removeButton.setOnClickListener {
            val tmpMoney = SelectedProducts.textMoneyPrice.value?.minus(products[position].price)
            val tmpPoints = SelectedProducts.textPointsPrice.value?.minus(products[position].price)
            selectedProducts.removeProduct(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
            SelectedProducts.amountOfProductsInBasket.value = SelectedProducts.amountOfProductsInBasket.value!! -1
            SelectedProducts.textMoneyPrice.value = tmpMoney
            SelectedProducts.textPointsPrice.value = tmpPoints
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }


    private fun showImage(img: ImageView, path: String) {
        val picturesDir = context.getDir("Pictures", Context.MODE_PRIVATE)
        val picturesDirPath = picturesDir?.absolutePath +"/"+ path

        val options = BitmapFactory.Options()
        options.inSampleSize = 8
        val bitmapPicture = BitmapFactory.decodeFile(picturesDirPath)
        img.setImageBitmap(bitmapPicture)
    }

}