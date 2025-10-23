package com.example.kidstodolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(private val cardList: List<CardItem>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = cardList[position]
        holder.title.text = item.title
        holder.image.setImageResource(item.imageResId)
    }

    override fun getItemCount(): Int = cardList.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.cardImage)
        val title: TextView = itemView.findViewById(R.id.cardTitle)
    }
}