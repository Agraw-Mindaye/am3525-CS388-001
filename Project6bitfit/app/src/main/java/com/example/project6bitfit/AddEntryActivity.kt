package com.example.project6bitfit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        // fields for adding food name and number od calories
        val foodNameEditText = findViewById<EditText>(R.id.food_name_eT)
        val caloriesEditText = findViewById<EditText>(R.id.calories_eT)
        val addButton = findViewById<Button>(R.id.add_entry_btn)

        val foodEntryDao = FoodDatabase.getDatabase(this).foodEntryDao()

        // add entry
        addButton.setOnClickListener {
            val name = foodNameEditText.text.toString().trim()
            val calories = caloriesEditText.text.toString().toIntOrNull() ?: 0

            if (name.isNotEmpty() && calories > 0) {
                val foodEntry = FoodEntry(name = name, calories = calories)

                // insert entry into database
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        foodEntryDao.insert(foodEntry)
                    }

                    finish()  // return to MainActivity
                }
            }
        }
    }
}
