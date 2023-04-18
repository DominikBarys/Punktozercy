package com.example.punktozercy.fragments.home.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.punktozercy.SelectedProducts
import com.example.punktozercy.databinding.ViewProductsBinding
import com.example.punktozercy.fragments.basket.BasketFragment
import com.example.punktozercy.fragments.basket.BasketViewModel
import com.example.punktozercy.mainscreen.MainScreenViewModel
import com.example.punktozercy.model.Product


class ProductsAdapter(private var products:List<Product>,private val context: Context,): RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    val selectedProducts: SelectedProducts = SelectedProducts()

    inner class ProductsViewHolder(binding: ViewProductsBinding): RecyclerView.ViewHolder(binding.root){
        val productName = binding.productName
        val productPrice = binding.moneyPrice
        val productPriceInPoints = binding.pointsPrice
        val productDescription = binding.productDescription
        val productPicture = binding.productPicture
        val buyButton = binding.buyButton
        val plusButton = binding.plusButton
        val minusButton = binding.minusButton
        val productAmount = binding.productAmount

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewProductsBinding = ViewProductsBinding.inflate(inflater, parent, false)
        return ProductsViewHolder(viewProductsBinding)
    }


    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {

        holder.productDescription.text = products[position].description
        holder.productName.text = products[position].name
        holder.productPrice.text = products[position].price.toString()
        holder.productPriceInPoints.text = products[position].pointsPrice.toString()
        showImage(holder.productPicture,products[position].imagePath!!)
        holder.productAmount.text = "1"

        holder.plusButton.setOnClickListener {
            var tmp = Integer.valueOf(holder.productAmount.text.toString())
            tmp += 1
            holder.productAmount.text = tmp.toString()
        }

        holder.minusButton.setOnClickListener {
            var tmp = Integer.valueOf(holder.productAmount.text.toString())
            if(tmp >= 2)
                tmp -= 1
            holder.productAmount.text = tmp.toString()
        }

        holder.buyButton.setOnClickListener {
            Toast.makeText(context, holder.productName.text.toString() + " x" +
                holder.productAmount.text.toString() + " added to basket", Toast.LENGTH_SHORT).show()

            for(i in 1..holder.productAmount.text.toString().toInt()) {
                selectedProducts.addProduct(products[position])
                SelectedProducts.amountOfProductsInBasket.value = SelectedProducts.amountOfProductsInBasket.value!! + 1
            }
            holder.productAmount.text = "1"
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