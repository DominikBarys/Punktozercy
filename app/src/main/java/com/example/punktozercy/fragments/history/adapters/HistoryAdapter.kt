package com.example.punktozercy.fragments.history.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.punktozercy.databinding.ViewHistoryBinding
import com.example.punktozercy.model.UserShoppingHistoryWithProduct

class HistoryAdapter(private var historyList: List<UserShoppingHistoryWithProduct>, private val context: Context ):
RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){



    inner class HistoryViewHolder(binding: ViewHistoryBinding):RecyclerView.ViewHolder(binding.root){
        val productName = binding.productName
        val productPicture = binding.productPicture
        val productPrice = binding.productPrice
        val date = binding.dateTextView

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHistoryBinding = ViewHistoryBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(viewHistoryBinding)
    }

    override fun getItemCount(): Int {
        return historyList.size

    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        val history = historyList[position]
        // Ustawienie daty
        if (position == 0 || history.userShoppingHistory.date != historyList[position - 1].userShoppingHistory.date) {
            holder.date.visibility = View.VISIBLE
            holder.date.text = history.userShoppingHistory.date
        } else {
            holder.date.visibility = View.GONE
        }
        holder.date.text = history.userShoppingHistory.date
        showImage(holder.productPicture,history.product.imagePath!!)
        holder.productName.text = history.product.name


        if(history.userShoppingHistory.isBoughtByMoney){
            holder.productPrice.text = history.product.price.toString() + " zł"
        }else{
            holder.productPrice.text = history.product.pointsPrice.toString() + " points"
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
