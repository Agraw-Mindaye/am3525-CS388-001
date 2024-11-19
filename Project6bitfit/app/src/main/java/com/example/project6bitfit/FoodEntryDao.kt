package com.example.project6bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodEntryDao {
    @Insert
    fun insert(foodEntry: FoodEntry)

    @Query("SELECT * FROM food_entry")
    fun getAllEntries(): Flow<List<FoodEntry>>

    @Query("DELETE FROM food_entry")
    fun clearAllEntries()
}