package com.example.mealplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealplanner.api.RecipeResponse

class RecipeSearchAdapter : RecyclerView.Adapter<RecipeSearchAdapter.ViewHolder>() {

    private var recipes: List<RecipeResponse> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.recipe_title)
        val recipeImage: ImageView = itemView.findViewById(R.id.recipe_image)

        fun bind(recipe: RecipeResponse) {
            titleText.text = recipe.title
            Glide.with(itemView.context).load(recipe.image).into(recipeImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.api_recipe_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    fun submitList(newRecipes: List<RecipeResponse>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
}
