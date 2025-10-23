package com.example.kidstodolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LanguageAdapter(
    private val languages: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    inner class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.language_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_language, parent, false)
        return LanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val language = languages[position]
        holder.textView.text = language

        val params = holder.textView.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = 16

        // Add horizontal spacing between items in same row
        val horizontalSpacing = 16

        if (position % 2 == 0) {
            // Left button
            params.marginStart = horizontalSpacing
            params.marginEnd = horizontalSpacing / 2
        } else {
            // Right button
            params.marginStart = horizontalSpacing / 2
            params.marginEnd = horizontalSpacing
        }

        holder.textView.layoutParams = params

        holder.textView.setOnClickListener {
            onClick(language)
        }
    }


    override fun getItemCount(): Int = languages.size
}
