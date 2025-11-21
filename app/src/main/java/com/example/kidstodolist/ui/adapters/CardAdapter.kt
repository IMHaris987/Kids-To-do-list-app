package com.example.kidstodolist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kidstodolist.data.model.CardItem
import com.example.kidstodolist.R

// ✅ Adapter class to bind a list of CardItem objects to RecyclerView items
class CardAdapter(private val cardList: List<CardItem>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    // Inflate each card layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    // Bind data to the views
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = cardList[position]
        holder.title.text = item.title
        holder.image.setImageResource(item.imageResId)
    }

    // Return number of cards
    override fun getItemCount(): Int = cardList.size

    // Holds references to each card’s UI elements
    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.cardImage)
        val title: TextView = itemView.findViewById(R.id.cardTitle)
    }
}