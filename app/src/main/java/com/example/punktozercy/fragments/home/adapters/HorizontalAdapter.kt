package com.example.punktozercy.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.punktozercy.databinding.ViewHorizontalBinding

class HorizontalAdapter(private val contacts: List<Contact>): RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

    inner class HorizontalViewHolder(binding: ViewHorizontalBinding): ViewHolder(binding.root){
        val nameTv = binding.nameTv
        val numberTv = binding.numberTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHorizontalBinding = ViewHorizontalBinding.inflate(inflater, parent, false)
        return HorizontalViewHolder(viewHorizontalBinding)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        holder.nameTv.text = contacts[position].name
        holder.numberTv.text = contacts[position].number
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}