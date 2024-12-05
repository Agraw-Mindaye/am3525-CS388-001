package com.example.mealplanner

import android.os.Bundle
import android.util.Log
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
import com.example.mealplanner.api.ApiClient
import com.example.mealplanner.api.RecipeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipesFragment : Fragment() {

    private lateinit var apiResultsAdapter: RecipeSearchAdapter
    private lateinit var databaseAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipes, container, false)

        // local recipes
        val recipeTitleInput = view.findViewById<EditText>(R.id.recipe_title_input)
        val recipeCaloriesInput = view.findViewById<EditText>(R.id.recipe_calories_input)
        val recipeIngredientsInput = view.findViewById<EditText>(R.id.recipe_ingredients_input)
        val addRecipeButton = view.findViewById<Button>(R.id.add_recipe_button)
        val localRecipesRecyclerView = view.findViewById<RecyclerView>(R.id.local_recipes_recycler_view)

        // API search
        val searchInput = view.findViewById<EditText>(R.id.search_input)
        val searchButton = view.findViewById<Button>(R.id.search_button)
        val apiResultsRecyclerView = view.findViewById<RecyclerView>(R.id.api_results_recycler_view)

        // adapters and RecyclerViews
        databaseAdapter = RecipeAdapter {}
        localRecipesRecyclerView.adapter = databaseAdapter
        localRecipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        apiResultsAdapter = RecipeSearchAdapter()
        apiResultsRecyclerView.adapter = apiResultsAdapter
        apiResultsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val recipeDao = AppDatabase.getDatabase(requireContext()).recipeDao()

        // load local recipes from db
        lifecycleScope.launch {
            val localRecipes = withContext(Dispatchers.IO) { recipeDao.getAllRecipes() }
            databaseAdapter.submitList(localRecipes)
        }

        // add recipe to local db
        addRecipeButton.setOnClickListener {
            val title = recipeTitleInput.text.toString()
            val calories = recipeCaloriesInput.text.toString().toIntOrNull()
            val ingredients = recipeIngredientsInput.text.toString()

            if (title.isNotEmpty() && calories != null && ingredients.isNotEmpty()) {
                val recipe = Recipe(title = title, calories = calories, ingredients = ingredients)

                lifecycleScope.launch(Dispatchers.IO) {
                    recipeDao.insertRecipe(recipe)
                    val updatedRecipes = recipeDao.getAllRecipes()

                    withContext(Dispatchers.Main) {
                        databaseAdapter.submitList(updatedRecipes)
                        Toast.makeText(requireContext(), "Recipe added!", Toast.LENGTH_SHORT).show()

                        recipeTitleInput.text.clear()
                        recipeCaloriesInput.text.clear()
                        recipeIngredientsInput.text.clear()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }

        // search for recipes with API
        searchButton.setOnClickListener {
            val ingredients = searchInput.text.toString()
            if (ingredients.isNotEmpty()) {
                fetchRecipes(ingredients)
            } else {
                Toast.makeText(requireContext(), "Please enter ingredients!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun fetchRecipes(ingredients: String) {
        val apiKey = BuildConfig.API_KEY
        val call = ApiClient.apiService.searchRecipes(ingredients, 5, apiKey)

        call.enqueue(object : Callback<List<RecipeResponse>> {
            override fun onResponse(
                call: Call<List<RecipeResponse>>,
                response: Response<List<RecipeResponse>>
            ) {
                if (response.isSuccessful) {
                    val recipes = response.body() ?: emptyList()
                    apiResultsAdapter.submitList(recipes)
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RecipeResponse>>, t: Throwable) {
                Log.e("API_ERROR", "Failed to fetch recipes: ${t.message}")
                Toast.makeText(requireContext(), "Failed to fetch recipes.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
