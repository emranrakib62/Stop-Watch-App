package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isRunning = false
    private var timerSeconds = 0

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            timerSeconds++
            val hours = timerSeconds / 3600
            val minutes = (timerSeconds % 3600) / 60
            val seconds = timerSeconds % 60

            val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            binding.timertext.text = time

            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start animation for the last TextView
        val animation = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom_slow)
        binding.tex.startAnimation(animation)

        val animatiion = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.te.startAnimation(animatiion)


        binding.startbtn.setOnClickListener {
            startTimer()
        }

        binding.stopbtn.setOnClickListener {
            stopTimer()
        }

        binding.reset.setOnClickListener {
            resetTimer()
        }

        updateButtonStates()
    }

    private fun startTimer() {
        if (!isRunning) {
            handler.postDelayed(runnable, 1000)
            isRunning = true
            binding.startbtn.text = "Pause"
            updateButtonStates()
        } else {
            stopTimer() // Pause functionality
        }
    }

    private fun stopTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable)
            isRunning = false
            binding.startbtn.text = "Resume"
            updateButtonStates()
        }
    }

    private fun resetTimer() {
        stopTimer()
        timerSeconds = 0
        binding.timertext.text = "00:00:00"
        binding.startbtn.text = "Start"
        updateButtonStates()
    }

    private fun updateButtonStates() {
        binding.startbtn.isEnabled = true
        binding.stopbtn.isEnabled = isRunning
        binding.reset.isEnabled = timerSeconds > 0
    }
}
