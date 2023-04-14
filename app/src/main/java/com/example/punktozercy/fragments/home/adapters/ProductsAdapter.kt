package com.example.punktozercy.fragments.home.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.punktozercy.databinding.ViewProductsBinding
import com.example.punktozercy.model.Product


class ProductsAdapter(private var products:List<Product>,private val context: Context,): RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    inner class ProductsViewHolder(binding: ViewProductsBinding): RecyclerView.ViewHolder(binding.root){
        val productName = binding.productName
        val productPrice = binding.productPrice
        val productPriceInPoints = binding.productPointPrice
        val productDescription = binding.productDescription
        val productPicture = binding.imageView4

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