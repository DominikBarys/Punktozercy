package com.example.punktozercy.fragments.history.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.punktozercy.databinding.ViewHistoryBinding
import com.example.punktozercy.model.UserShoppingHistoryWithProduct

/**
 * class responsible for showing products with date in history fragment.
 * @param historyList product list with dates
 * @param context application context
 * @property HistoryViewHolder
 * @property onCreateViewHolder
 * @property getItemCount
 * @property onBindViewHolder
 * @property showImage
 */
class HistoryAdapter(private var historyList: List<UserShoppingHistoryWithProduct>, private val context: Context ):
RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){


    /**
     * class responsible for single element in history adapter
     */
    inner class HistoryViewHolder(binding: ViewHistoryBinding):RecyclerView.ViewHolder(binding.root){
        val productName = binding.productName
        val productPicture = binding.productPicture
        val productPrice = binding.productPrice
        val date = binding.dateTextView

    }


    /**
     * function that is called when new instance of view holder is created
     * @param parent
     * @param viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHistoryBinding = ViewHistoryBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(viewHistoryBinding)
    }

    /**
     * function that returns size of history list
     * @return size of the history list
     */
    override fun getItemCount(): Int {
        return historyList.size

    }

    /**
     * function that is called to show data from history view holder
     * @param holder instance of history view holder
     * @param position position of element
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        val history = historyList[position]
        // setting date
        if (position == 0 || history.userShoppingHistory.date != historyList[position - 1].userShoppingHistory.date) {
            holder.date.visibility = View.VISIBLE
            holder.date.text = history.userShoppingHistory.date
        } else {
            holder.date.visibility = View.GONE
        }
        // set text views
        holder.date.text = history.userShoppingHistory.date
        showImage(holder.productPicture,history.product.imagePath!!)
        holder.productName.text = history.product.name

        // check if product is bought by money or points
        if(history.userShoppingHistory.isBoughtByMoney){
            holder.productPrice.text = history.product.price.toString() + " zł"
        }else{
            holder.productPrice.text = history.product.pointsPrice.toString() + " points"
        }




    }

    /**
     * function that is responsible for showing images without using resources
     * @param img image view object
     * @param path path to the image
     */
    private fun showImage(img: ImageView, path: String) {
        val picturesDir = context.getDir("Pictures", Context.MODE_PRIVATE)
        val picturesDirPath = picturesDir?.absolutePath +"/"+ path

        val options = BitmapFactory.Options()
        options.inSampleSize = 8
        val bitmapPicture = BitmapFactory.decodeFile(picturesDirPath)
        img.setImageBitmap(bitmapPicture)
    }

}
