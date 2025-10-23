package com.example.kidstodolist

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener

class MenuFragment : Fragment() {

    private lateinit var languageOption: TextView
    private lateinit var soundCheckbox: CheckBox
    private lateinit var soundOption: TextView
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languageOption = view.findViewById(R.id.language_option)
        soundCheckbox = view.findViewById(R.id.sound_checkbox)
        soundOption = view.findViewById(R.id.sound_option)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.tick_sound)

        setupListeners()

        // ✅ Listen for language selection result
        setFragmentResultListener("language_result") { key, bundle ->
            val selectedLanguage = bundle.getString("selected_language")
            if (selectedLanguage != null) {
                languageOption.text = selectedLanguage
            }
        }
    }

    private fun setupListeners() {
        // ✅ Clicking language opens LanguageSelectionFragment
        languageOption.setOnClickListener {
            if (soundCheckbox.isChecked) playTickSound()
            (activity as? MainActivity)?.closeDrawerAndOpenLanguageFragment()
        }

        soundOption.setOnClickListener {
            soundCheckbox.isChecked = !soundCheckbox.isChecked
        }

        soundCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) playTickSound()
        }
    }

    private fun playTickSound() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(0)
        }
        mediaPlayer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer.release()
    }
}
