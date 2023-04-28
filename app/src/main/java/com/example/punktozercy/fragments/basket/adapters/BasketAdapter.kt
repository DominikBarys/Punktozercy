package com.example.punktozercy.fragments.basket.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.punktozercy.SharedData
import com.example.punktozercy.databinding.ViewBasketBinding
import com.example.punktozercy.model.Product

/**
 * class responsible for showing products selected by user in basket fragment
 * @param products list of selected products
 * @param context application context
 * @property sharedData
 * @property BasketViewHolder
 * @property onCreateViewHolder
 * @property onBindViewHolder
 * @property getItemCount
 * @property showImage
 */
class BasketAdapter(private val products: List<Product>, private val context: Context) : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    /**
     * An object of the class in which methods are needed to modify the list of products
     */
    var sharedData = SharedData()

    /**
     * class responsible for single element in history adapter
     */
    inner class BasketViewHolder(binding: ViewBasketBinding) :ViewHolder(binding.root){
        val productPicture = binding.basketProductPicture
        val productName = binding.basketProduktName
        val productMoneyValue = binding.basketProductMoneyValue
        val productPointsValue = binding.basketProductPointsValue
        val removeButton = binding.removeButton
    }

    /**
     * function that is called when new instance of view holder is created
     * @param parent
     * @param viewType
     * @return returns inflated view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBasketBinding = ViewBasketBinding.inflate(inflater, parent, false)
        return BasketViewHolder(viewBasketBinding)
    }

    /**
     * function that is called to show data from basket view holder
     * @param holder instance of basket view holder
     * @param position position of element
     */
    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.productName.text = products[position].name
        holder.productMoneyValue.text = "Price in zł: " + products[position].price.toString() + " zł"
        holder.productPointsValue.text = "Price in points: " + products[position].pointsPrice.toString() + " points"
        showImage(holder.productPicture, products[position].imagePath!!)
        holder.removeButton.setOnClickListener {
            val tmpMoney = SharedData.textMoneyPrice.value?.minus(products[position].price)
            val tmpPoints = SharedData.textPointsPrice.value?.minus(products[position].pointsPrice!!)
            sharedData.removeProduct(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
            SharedData.amountOfProductsInBasket.value = SharedData.amountOfProductsInBasket.value!! -1
            SharedData.textMoneyPrice.value = tmpMoney
            SharedData.textPointsPrice.value = tmpPoints
        }
    }

    /**
     * function that returns size of basket list
     * @return size of the history list
     */
    override fun getItemCount(): Int {
        return products.size
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