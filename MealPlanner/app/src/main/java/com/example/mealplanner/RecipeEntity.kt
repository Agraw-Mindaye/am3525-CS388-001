package com.example.mealplanner

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val calories: Int,
    val ingredients: String, // Store ingredients as a comma-separated string
    val day: String? = null // Day of the week (e.g., "Monday"), null if unsaved
)
