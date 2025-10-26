package com.example.kidstodolist

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import java.util.*

class VoiceInputFragment : Fragment() {

    private lateinit var titleText: TextView
    private lateinit var stepTitle: TextView
    private lateinit var cardImage: ImageView
    private lateinit var imageCard: CardView
    private lateinit var recordingProgress: ProgressBar
    private lateinit var micButton: ImageButton
    private lateinit var backButton: ImageButton

    private var isRecording = false

    // 🎤 Speech recognizer intent launcher
    private val speechLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val spokenText =
                    result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                spokenText?.let {
                    Toast.makeText(requireContext(), "You said: $it", Toast.LENGTH_LONG).show()
                    // You can update UI or save the voice input here
                    stopListeningAnimation()
                }
            } else {
                stopListeningAnimation()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_voice_input, container, false)

        // Initialize views
        titleText = view.findViewById(R.id.titleText)
        stepTitle = view.findViewById(R.id.stepTitle)
        cardImage = view.findViewById(R.id.cardImage)
        imageCard = view.findViewById(R.id.imageCard)
        recordingProgress = view.findViewById(R.id.recordingProgress)
        micButton = view.findViewById(R.id.micButton)
        backButton = view.findViewById(R.id.backButton)

        // Back button
        backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Initialize progress bar
        recordingProgress.progress = 0
        recordingProgress.max = 100

        // 📸 Get image URI from arguments
        val imageUriString = arguments?.getString("image_uri")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            cardImage.setImageURI(imageUri)
            cardImage.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            Toast.makeText(requireContext(), "No image received", Toast.LENGTH_SHORT).show()
            // Set a placeholder or default image
            cardImage.setImageResource(android.R.color.darker_gray)
        }

        // Microphone button click
        micButton.setOnClickListener {
            if (isRecording) {
                // Stop recording
                stopListeningManually()
            } else {
                // Start recording
                startListening()
            }
        }

        return view
    }

    // 🎧 Start speech recognition
    private fun startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(requireContext())) {
            Toast.makeText(
                requireContext(),
                "Voice input not supported on this device",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")

        isRecording = true
        startListeningAnimation()
        speechLauncher.launch(intent)
    }

    // 🎬 Mic animation & progress simulation while listening
    private fun startListeningAnimation() {
        // Change icon to cross/close
        micButton.setImageResource(R.drawable.ic_close)

        // Pulse animation on microphone button
        val scaleAnim = ScaleAnimation(
            1f, 1.15f, 1f, 1.15f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnim.duration = 600
        scaleAnim.repeatCount = Animation.INFINITE
        scaleAnim.repeatMode = Animation.REVERSE
        micButton.startAnimation(scaleAnim)

        // Animate progress bar
        recordingProgress.visibility = View.VISIBLE
        animateProgress()
    }

    // 📊 Animate progress bar from 0 to 100
    private fun animateProgress() {
        recordingProgress.progress = 0
        val maxProgress = 100
        val duration = 3000L // 3 seconds
        val startTime = System.currentTimeMillis()

        val handler = android.os.Handler(android.os.Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val elapsed = System.currentTimeMillis() - startTime
                val progress = ((elapsed.toFloat() / duration) * maxProgress).toInt()

                if (progress <= maxProgress && isRecording) {
                    recordingProgress.progress = progress
                    handler.postDelayed(this, 30)
                }
            }
        }
        handler.post(runnable)
    }

    // 🛑 Stop recording manually (when cross is clicked)
    private fun stopListeningManually() {
        isRecording = false
        stopListeningAnimation()
    }

    // 🛑 Stop mic animation & reset progress
    private fun stopListeningAnimation() {
        isRecording = false
        micButton.clearAnimation()

        // Change icon back to microphone
        micButton.setImageResource(R.drawable.ic_microphone)

        recordingProgress.progress = 0

        // Fade out progress bar
        recordingProgress.animate()
            .alpha(0f)
            .setDuration(500)
            .withEndAction {
                recordingProgress.visibility = View.INVISIBLE
                recordingProgress.alpha = 1f
            }
    }
}