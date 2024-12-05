package com.example.mealplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.AppDatabase
import com.example.mealplanner.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealsFragment : Fragment() {

    private lateinit var unsavedAdapter: RecipeAdapter
    private lateinit var mealsAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meals, container, false)

        val unsavedRecyclerView = view.findViewById<RecyclerView>(R.id.unsaved_recipes_recycler_view)
        val mealsRecyclerView = view.findViewById<RecyclerView>(R.id.meals_recycler_view)

        // set up RecyclerViews and adapters
        unsavedAdapter = RecipeAdapter { recipe ->
            showDaySelectionDialog(recipe) // for unsaved recipes
        }
        mealsAdapter = RecipeAdapter { recipe ->
            if (recipe.day != null) {
                showShoppingList(recipe) // show shopping list for saved meals
            }
        }

        unsavedRecyclerView.adapter = unsavedAdapter
        unsavedRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        mealsRecyclerView.adapter = mealsAdapter
        mealsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val recipeDao = AppDatabase.getDatabase(requireContext()).recipeDao()

        // load unsaved recipes
        lifecycleScope.launch {
            val unsavedRecipes = withContext(Dispatchers.IO) { recipeDao.getUnsavedRecipes() }
            unsavedAdapter.submitList(unsavedRecipes)
        }

        // load saved meals
        lifecycleScope.launch {
            val meals = withContext(Dispatchers.IO) { recipeDao.getAllRecipes().filter { it.day != null } }
            meals.forEach { meal -> // Debugging meals
                println("Meal: ${meal.title}, Day: ${meal.day}")
            }
            mealsAdapter.submitList(meals)
        }

        return view
    }

    private fun showDaySelectionDialog(recipe: Recipe) {
        val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Select a Day")
            .setItems(days) { _, which ->
                val selectedDay = days[which]
                assignRecipeToDay(recipe, selectedDay)
            }
            .show()
    }

    private fun assignRecipeToDay(recipe: Recipe, day: String) {
        val recipeDao = AppDatabase.getDatabase(requireContext()).recipeDao()

        lifecycleScope.launch(Dispatchers.IO) {
            // update recipe and turn it to a saved meal
            val updatedRecipe = recipe.copy(day = day)
            recipeDao.updateRecipe(updatedRecipe)

            val meals = recipeDao.getAllRecipes().filter { it.day != null }
            val unsavedRecipes = recipeDao.getUnsavedRecipes()

            withContext(Dispatchers.Main) {
                mealsAdapter.submitList(meals)
                unsavedAdapter.submitList(unsavedRecipes)
                Toast.makeText(requireContext(), "Recipe saved to $day!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showShoppingList(recipe: Recipe) {
        // get the ingredients withouttheir  measurements
        val cleanIngredients = recipe.ingredients.split(",").map { it.trim() }

        // show the shopping list
        ShoppingListDialogFragment.newInstance(cleanIngredients)
            .show(parentFragmentManager, "shopping_list_dialog")
    }
}
