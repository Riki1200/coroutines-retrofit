package com.example.ktoradapter.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ktoradapter.R;
import com.squareup.picasso.*;
import com.example.ktoradapter.databinding.ItemDogBinding;



class  DogsAdapter (val images: List<String>): RecyclerView.Adapter<DogsAdapter.DogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }
    override fun getItemCount(): Int = images.size
    inner class DogViewHolder(viewHolder: View): RecyclerView.ViewHolder(viewHolder) {
        private var bindingHolder = ItemDogBinding.bind(viewHolder);
        fun bind(image: String) {
            Picasso.get().load(image).into(bindingHolder.imgDog)
        }
    }
}


