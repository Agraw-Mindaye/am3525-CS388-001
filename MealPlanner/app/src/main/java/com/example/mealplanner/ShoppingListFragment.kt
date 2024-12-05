package com.example.mealplanner

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ShoppingListDialogFragment(private val ingredients: List<String>) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Shopping List")
        builder.setItems(ingredients.toTypedArray(), null) // Display ingredients as a list
        builder.setPositiveButton("OK") { _, _ -> dismiss() }
        return builder.create()
    }

    companion object {
        fun newInstance(ingredients: List<String>): ShoppingListDialogFragment {
            return ShoppingListDialogFragment(ingredients)
        }
    }
}
