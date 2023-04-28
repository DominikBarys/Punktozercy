package com.example.punktozercy.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.punktozercy.databinding.ViewHorizontalBinding

/**
 * class responsible for showing offers of products in home fragment
 * @param offers list of offers
 * @property onItemClick
 * @property OfferAdapter
 * @property setFilteredList
 * @property onCreateViewHolder
 * @property onBindViewHolder
 * @property getItemCount
 */
class OfferAdapter(private var offers: List<Offer>): RecyclerView.Adapter<OfferAdapter.HorizontalViewHolder>() {

    /**
     * variable used to recognise which offer from list was clicked and then to redirect the user
     * to the snippet with the relevant products
     */
    var onItemClick: ((Offer) -> Unit)? = null

    /**
     * class responsible for single element in offer adapter
     */
    inner class HorizontalViewHolder(binding: ViewHorizontalBinding): ViewHolder(binding.root){
        val offerImage = binding.offerImage
        val offerDescription = binding.offerDescription
        lateinit var offerCategory: String
    }

    /**
     * function used when user filters list of offers
     * @param offers filtered list of offers
     */
    fun setFilteredList(offers: List<Offer>){
        this.offers = offers
        notifyDataSetChanged()
    }

    /**
     * function that is called when new instance of view holder is created
     * @param parent
     * @param viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHorizontalBinding = ViewHorizontalBinding.inflate(inflater, parent, false)
        return HorizontalViewHolder(viewHorizontalBinding)
    }

    /**
     * function that is called to show data from home view holder
     * @param holder instance of home view holder
     * @param position position of element
     */
    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        val pozycja = offers[position]

        holder.offerImage.setImageResource(offers[position].drawableId)
        holder.offerDescription.text = offers[position].description
        holder.offerCategory = offers[position].category

        holder.offerImage.setOnClickListener{
            onItemClick?.invoke(pozycja)
        }
    }

    /**
     * function that returns size of offer list
     * @return size of the offer list
     */
    override fun getItemCount(): Int {
        return offers.size
    }
}