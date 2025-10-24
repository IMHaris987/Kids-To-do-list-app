package com.example.kidstodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LanguageSelectionFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val languages = listOf("English", "Urdu", "Spanish", "French", "German", "Chinese")

    // Optional callback for notifying parent fragment/activity
    private var languageSelectedListener: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate language selection layout
        return inflater.inflate(R.layout.fragment_language_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.language_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set adapter with click listener for each language
        recyclerView.adapter = LanguageAdapter(languages) { selectedLanguage ->
            applyLanguage(selectedLanguage)
        }
    }

    /**
     * Apply chosen language and refresh UI.
     */
    private fun applyLanguage(selectedLanguage: String) {
        val languageCode = when (selectedLanguage) {
            "English" -> "en"
            "Urdu" -> "ur"
            "Spanish" -> "es"
            "French" -> "fr"
            "German" -> "de"
            "Chinese" -> "zh"
            else -> "en"
        }

        // Update locale globally
        LocaleHelper.setLocale(requireContext(), languageCode)

        // Notify listener if set
        languageSelectedListener?.invoke(selectedLanguage)

        // Refresh activity to apply new language instantly
        requireActivity().recreate()

        // Return to previous fragment
        parentFragmentManager.popBackStack()
    }

    // Allow external listener setup
    fun setLanguageSelectedListener(listener: (String) -> Unit) {
        languageSelectedListener = listener
    }

    // ================= Adapter ===================
    class LanguageAdapter(
        private val languages: List<String>,
        private val onLanguageClick: (String) -> Unit
    ) : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_language, parent, false)
            return LanguageViewHolder(view, onLanguageClick)
        }

        override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
            holder.bind(languages[position])
        }

        override fun getItemCount(): Int = languages.size

        // ViewHolder to display each language option
        class LanguageViewHolder(
            itemView: View,
            private val onLanguageClick: (String) -> Unit
        ) : RecyclerView.ViewHolder(itemView) {

            private val languageText: TextView = itemView.findViewById(R.id.language_text)

            fun bind(language: String) {
                languageText.text = language
                itemView.setOnClickListener {
                    onLanguageClick(language)
                }
            }
        }
    }
}
