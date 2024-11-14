package com.example.project6bitfit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_entry")
data class FoodEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val calories: Int
)
