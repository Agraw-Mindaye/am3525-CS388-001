package com.example.project6bitfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodEntryAdapter(private val foodEntries: MutableList<FoodEntry>) :
    RecyclerView.Adapter<FoodEntryAdapter.FoodEntryViewHolder>() {

    class FoodEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodNameTextView: TextView = itemView.findViewById(R.id.food_name_tV)
        val caloriesTextView: TextView = itemView.findViewById(R.id.calories_tV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodEntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food_entry, parent, false)
        return FoodEntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodEntryViewHolder, position: Int) {
        val foodEntry = foodEntries[position]
        holder.foodNameTextView.text = foodEntry.name
        holder.caloriesTextView.text = foodEntry.calories.toString()
    }

    override fun getItemCount() = foodEntries.size

    // Function to update entries
    fun updateEntries(newEntries: List<FoodEntry>) {
        foodEntries.clear()
        foodEntries.addAll(newEntries)
        notifyDataSetChanged()
    }
}
