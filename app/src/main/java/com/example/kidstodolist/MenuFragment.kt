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

    // UI elements
    private lateinit var languageOption: TextView
    private lateinit var soundCheckbox: CheckBox
    private lateinit var soundOption: TextView

    // Used to play tick sound
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout for the menu screen
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        languageOption = view.findViewById(R.id.language_option)
        soundCheckbox = view.findViewById(R.id.sound_checkbox)
        soundOption = view.findViewById(R.id.sound_option)

        // Prepare tick sound from raw resource
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.tick_sound)

        // Set up all click and change listeners
        setupListeners()

        // ✅ Receive language selection result from LanguageSelectionFragment
        setFragmentResultListener("language_result") { _, bundle ->
            val selectedLanguage = bundle.getString("selected_language")
            if (selectedLanguage != null) {
                languageOption.text = selectedLanguage
            }
        }
    }

    private fun setupListeners() {
        // When language text is clicked → navigate to language selection
        languageOption.setOnClickListener {
            if (soundCheckbox.isChecked) playTickSound()
            (activity as? MainActivity)?.closeDrawerAndOpenLanguageFragment()
        }

        // Clicking "Sound" text toggles the checkbox
        soundOption.setOnClickListener {
            soundCheckbox.isChecked = !soundCheckbox.isChecked
        }

        // When checkbox is checked → play tick sound
        soundCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) playTickSound()
        }
    }

    // ✅ Plays the tick sound, restarting it if already playing
    private fun playTickSound() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(0)
        }
        mediaPlayer.start()
    }

    // Release the MediaPlayer when fragment view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer.release()
    }
}
