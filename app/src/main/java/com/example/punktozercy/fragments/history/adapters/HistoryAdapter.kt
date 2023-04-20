package com.example.punktozercy.fragments.history.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.punktozercy.databinding.ViewHistoryBinding
import com.example.punktozercy.databinding.ViewPogchampBinding
import com.example.punktozercy.databinding.ViewProductsBinding
import com.example.punktozercy.model.Product

class HistoryAdapter(private var history: Map<String, List<Product>>,private val context: Context ):
RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){



    inner class HistoryViewHolder(binding: ViewHistoryBinding):RecyclerView.ViewHolder(binding.root){
        //val productName = binding.productName
       // val productPicture = binding.productPicture
        val date = binding.dateTextView
        val productRecyclerView = binding.productRecyclerView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHistoryBinding = ViewHistoryBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(viewHistoryBinding)
    }

    override fun getItemCount(): Int {
        return history.values.sumOf { it.size } //history.size

    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

//        for((key,products) in history){
//            holder.date.text = key.toString()
//            for(product in products){
//                showImage(holder.productPicture,product.imagePath!!)
//                holder.productName.text = product.name
//            }
//        }

        val keyList = history.keys.toList()
        if(position < keyList.size){
            val key = keyList[position]
            val productList = history[key]
            holder.date.text = key
            holder.productRecyclerView.adapter = ProductAdapter(productList!!,context)
            holder.productRecyclerView.layoutManager = LinearLayoutManager(context)
        }





    }

//    private fun showImage(img: ImageView, path: String) {
//        val picturesDir = context.getDir("Pictures", Context.MODE_PRIVATE)
//        val picturesDirPath = picturesDir?.absolutePath +"/"+ path
//
//        val options = BitmapFactory.Options()
//        options.inSampleSize = 8
//        val bitmapPicture = BitmapFactory.decodeFile(picturesDirPath)
//        img.setImageBitmap(bitmapPicture)
//    }

}

class ProductAdapter(private val productList: List<Product>,private val context: Context):RecyclerView.Adapter<ProductAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHistoryBinding = ViewPogchampBinding.inflate(inflater, parent, false)
        return ViewHolder(viewHistoryBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = productList[position].name
        showImage(holder.productPicture,productList[position].imagePath!!)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(binding: ViewPogchampBinding) : RecyclerView.ViewHolder(binding.root){
        val productName = binding.nameTextView
        val productPicture = binding.productPicture
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