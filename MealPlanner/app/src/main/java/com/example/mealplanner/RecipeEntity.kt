package com.example.mealplanner

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val calories: Int,
    val ingredients: String, // store ingredients
    val day: String? = null // day of week
)
