package com.example.kidstodolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for displaying a grid or list of language options.
 */
class LanguageAdapter(
    private val languages: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    // Holds reference to TextView for each language
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

        // Add spacing and margin styling dynamically
        val params = holder.textView.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = 16
        val horizontalSpacing = 16

        // Apply different margins for left/right positions
        if (position % 2 == 0) {
            params.marginStart = horizontalSpacing
            params.marginEnd = horizontalSpacing / 2
        } else {
            params.marginStart = horizontalSpacing / 2
            params.marginEnd = horizontalSpacing
        }

        holder.textView.layoutParams = params

        // Click callback for selected language
        holder.textView.setOnClickListener {
            onClick(language)
        }
    }

    override fun getItemCount(): Int = languages.size
}
