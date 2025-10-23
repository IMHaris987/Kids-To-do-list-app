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

    // Optional: Callback if parent wants to be notified
    private var languageSelectedListener: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_language_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.language_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = LanguageAdapter(languages) { selectedLanguage ->
            applyLanguage(selectedLanguage)
        }
    }

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

        // Update locale using helper
        LocaleHelper.setLocale(requireContext(), languageCode)

        // Optional: notify parent activity if needed
        languageSelectedListener?.invoke(selectedLanguage)

        // Restart activity to apply the language change immediately
        requireActivity().recreate()

        // Optionally pop the fragment if you want to go back
        parentFragmentManager.popBackStack()
    }

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

