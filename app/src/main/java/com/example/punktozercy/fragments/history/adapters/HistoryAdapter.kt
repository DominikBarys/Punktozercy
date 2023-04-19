package com.example.punktozercy.fragments.history.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.punktozercy.databinding.ViewHistoryBinding
import com.example.punktozercy.model.Product

class HistoryAdapter(private var history: Map<String, List<Product>>,private val context: Context ):
RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

    private val datesList = history.keys.toList()

    inner class HistoryViewHolder(binding: ViewHistoryBinding):RecyclerView.ViewHolder(binding.root){
        val productName = binding.productName
        val productPicture = binding.productPicture
        val date = binding.historyDate
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHistoryBinding = ViewHistoryBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(viewHistoryBinding)
    }

    override fun getItemCount(): Int {
        return history.values.sumOf { it.size }
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        for((key,products) in history){
            holder.date.text = key.toString()
            for(product in products){
                showImage(holder.productPicture,product.imagePath!!)
                holder.productName.text = product.name
            }
        }


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