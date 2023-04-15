package com.example.punktozercy.fragments.home.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.punktozercy.databinding.ViewHorizontalBinding

class HorizontalAdapter(private var offers: List<Offer>): RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

    var onItemClick: ((Offer) -> Unit)? = null


    inner class HorizontalViewHolder(binding: ViewHorizontalBinding): ViewHolder(binding.root){
        val offerImage = binding.offerImage
        val offerDescription = binding.offerDescription
        lateinit var offerCategory: String
    }

    fun setFilteredList(offers: List<Offer>){
        this.offers = offers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHorizontalBinding = ViewHorizontalBinding.inflate(inflater, parent, false)
        return HorizontalViewHolder(viewHorizontalBinding)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        val pozycja = offers[position]

        holder.offerImage.setImageResource(offers[position].drawableId)
        holder.offerDescription.text = offers[position].description
        holder.offerCategory = offers[position].category

        holder.offerImage.setOnClickListener{
            onItemClick?.invoke(pozycja)
        }

    }
    override fun getItemCount(): Int {
        return offers.size
    }
}