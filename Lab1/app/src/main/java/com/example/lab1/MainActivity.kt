package com.example.lab1

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var taps = 0
    var increment = 1
    var upgradeShown = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.b1)
        val imageButton = findViewById<ImageButton>(R.id.b2)
        val upgradeButton = findViewById<Button>(R.id.upgradeButton)
        val iconButton = findViewById<Button>(R.id.iconButton)
        val textView = findViewById<TextView>(R.id.textView)

        button.setOnClickListener{
            taps += increment
            textView.text = taps.toString()

            if (taps >= 10 && !upgradeShown) {
                Toast.makeText(this, "Upgrade available for a cost of 10 taps!", Toast.LENGTH_SHORT).show()
                upgradeShown = true
            }
        }

        upgradeButton.setOnClickListener(){
            if (taps < 10) {
                Toast.makeText(it.context, "Upgrade unavailable, not enough taps yet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            taps -= 10
            textView.text = taps.toString()
            Toast.makeText(it.context, "You have paid 10 taps and upgraded to double taps!", Toast.LENGTH_SHORT).show()
            increment = 2

            // disable both buttons once user selects an upgrade
            upgradeButton.isEnabled = false
            iconButton.isEnabled = false
        }

        iconButton.setOnClickListener(){
            if (taps < 10) {
                Toast.makeText(it.context, "Upgrade unavailable, not enough taps yet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            taps -= 10
            textView.text = taps.toString()
            Toast.makeText(it.context, "You have paid 10 taps and upgraded to a custom button icon!", Toast.LENGTH_SHORT).show()

            // Hide the old button and show the ImageButton
            button.visibility = Button.GONE  // Hide the old button
            imageButton.visibility = ImageButton.VISIBLE

            // disable both buttons once user selects an upgrade
            iconButton.isEnabled = false
            upgradeButton.isEnabled = false
        }

        imageButton.setOnClickListener {
            taps += increment
            textView.text = taps.toString()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}