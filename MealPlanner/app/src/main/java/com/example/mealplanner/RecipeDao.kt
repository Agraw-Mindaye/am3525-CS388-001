package com.example.mealplanner

import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe)

    @Update
    fun updateRecipe(recipe: Recipe)

    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes WHERE day IS NULL ORDER BY title ASC")
    fun getUnsavedRecipes(): List<Recipe>

    @Query("SELECT * FROM recipes WHERE day = :day ORDER BY title ASC")
    fun getMealsForDay(day: String): List<Recipe>

    @Query("SELECT * FROM recipes ORDER BY title ASC")
    fun getAllRecipes(): List<Recipe>


}
