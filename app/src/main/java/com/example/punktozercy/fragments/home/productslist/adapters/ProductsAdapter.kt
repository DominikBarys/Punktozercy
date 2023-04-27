package com.example.punktozercy.fragments.home.productslist.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.punktozercy.SelectedProducts
import com.example.punktozercy.databinding.ViewProductsBinding
import com.example.punktozercy.model.Product

/**
 * class responsible for showing product list in product list fragment.
 * @param products product list
 * @param context application context
 * @property ProductsViewHolder
 * @property onCreateViewHolder
 * @property getItemCount
 * @property onBindViewHolder
 * @property showImage
 */
class ProductsAdapter(private var products:List<Product>,private val context: Context,): RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {


    /**
     * variable for storing selected products by user
     */
    private val selectedProducts: SelectedProducts = SelectedProducts()

    /**
     * class responsible for single element in products adapter
     */
    inner class ProductsViewHolder(binding: ViewProductsBinding): RecyclerView.ViewHolder(binding.root){
        val productName = binding.productName
        val productPrice = binding.moneyPrice
        val productPriceInPoints = binding.pointsPrice
        val productPicture = binding.productPicture
        val buyButton = binding.buyButton
        val plusButton = binding.plusButton
        val minusButton = binding.minusButton
        val productAmount = binding.productAmount

    }
    /**
     * function that is called when new instance of view holder is created
     * @param parent
     * @param viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewProductsBinding = ViewProductsBinding.inflate(inflater, parent, false)
        return ProductsViewHolder(viewProductsBinding)
    }

    /**
     * function that is called to show data from product view holder
     * @param holder instance of history view holder
     * @param position position of element
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {


        holder.productName.text = products[position].name
        holder.productPrice.text = "Price in zł: ${products[position].price.toString()}"
        holder.productPriceInPoints.text = "Price in points: ${products[position].pointsPrice.toString()}"
        showImage(holder.productPicture,products[position].imagePath!!)
        holder.productAmount.text = "1"

        /**
         * add product amount after button is clicked
         */
        holder.plusButton.setOnClickListener {
            var tmp = Integer.valueOf(holder.productAmount.text.toString())
            tmp += 1
            holder.productAmount.text = tmp.toString()
        }
        /**
         * decrease product amount after button is clicked
         */
        holder.minusButton.setOnClickListener {
            var tmp = Integer.valueOf(holder.productAmount.text.toString())
            if(tmp >= 2)
                tmp -= 1
            holder.productAmount.text = tmp.toString()
        }
        /**
         * add product to basket after button is clicked
         */
        holder.buyButton.setOnClickListener {
            for(i in 1..holder.productAmount.text.toString().toInt()) {
                selectedProducts.addProduct(products[position])
                SelectedProducts.amountOfProductsInBasket.value = SelectedProducts.amountOfProductsInBasket.value!! + 1
            }
            holder.productAmount.text = "1"
        }
    }
    /**
     * function that returns size of product list
     * @return size of the product list
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