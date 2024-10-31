package com.example.project5bitfit

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [FoodEntry::class], version = 1)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodEntryDao(): FoodEntryDao

    companion object {
        @Volatile private var instance: FoodDatabase? = null

        fun getDatabase(context: Context): FoodDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java, "food_database"
                ).build().also { instance = it }
            }
        }
    }
}
