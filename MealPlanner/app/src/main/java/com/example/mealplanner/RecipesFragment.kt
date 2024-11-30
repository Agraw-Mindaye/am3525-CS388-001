package com.example.mealplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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

class RecipesFragment : Fragment() {

    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recipes_recycler_view)
        val titleInput = view.findViewById<EditText>(R.id.recipe_title_input)
        val caloriesInput = view.findViewById<EditText>(R.id.recipe_calories_input)
        val ingredientsInput = view.findViewById<EditText>(R.id.recipe_ingredients_input)
        val addButton = view.findViewById<Button>(R.id.add_recipe_button)

        // Set up RecyclerView and Adapter with click listener
        adapter = RecipeAdapter { recipe ->
            // Example action for recipe item clicks
            Toast.makeText(requireContext(), "Clicked on: ${recipe.title}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize Room database and DAO
        val recipeDao = AppDatabase.getDatabase(requireContext()).recipeDao()

        // Load recipes from the database
        lifecycleScope.launch {
            val recipes = withContext(Dispatchers.IO) { recipeDao.getAllRecipes() }
            adapter.submitList(recipes)
        }

        // Add recipe to the database
        addButton.setOnClickListener {
            val title = titleInput.text.toString()
            val calories = caloriesInput.text.toString().toIntOrNull()
            val ingredients = ingredientsInput.text.toString()

            if (title.isNotEmpty() && calories != null && ingredients.isNotEmpty()) {
                val recipe = Recipe(title = title, calories = calories, ingredients = ingredients)

                lifecycleScope.launch(Dispatchers.IO) {
                    recipeDao.insertRecipe(recipe)
                    val updatedRecipes = recipeDao.getAllRecipes()

                    withContext(Dispatchers.Main) {
                        adapter.submitList(updatedRecipes)
                        Toast.makeText(requireContext(), "Recipe added!", Toast.LENGTH_SHORT).show()

                        // Clear the input fields
                        titleInput.text.clear()
                        caloriesInput.text.clear()
                        ingredientsInput.text.clear()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
