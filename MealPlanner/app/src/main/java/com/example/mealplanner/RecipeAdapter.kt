package com.example.mealplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecipeAdapter(
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipes: List<Recipe> = emptyList()

    class RecipeViewHolder(itemView: View, private val onItemClick: (Recipe) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.recipe_title)
        private val caloriesText: TextView = itemView.findViewById(R.id.recipe_calories)
        private val ingredientsText: TextView = itemView.findViewById(R.id.recipe_ingredients)

        fun bind(recipe: Recipe) {
            val displayTitle = if (recipe.day != null) {
                "${recipe.title} (${recipe.day})" // Append the day if it's saved as a meal
            } else {
                recipe.title
            }

            titleText.text = displayTitle
            caloriesText.text = "Calories: ${recipe.calories}"
            ingredientsText.text = "Ingredients: ${recipe.ingredients}"

            itemView.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    fun submitList(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}
