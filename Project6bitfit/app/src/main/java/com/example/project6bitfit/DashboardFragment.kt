package com.example.project6bitfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.project6bitfit.databinding.FragmentDashboardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foodEntryDao = FoodDatabase.getDatabase(requireContext()).foodEntryDao()

        // calculate and display summary
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val entries = foodEntryDao.getAllEntries().first()

                if (entries.isNotEmpty()) {
                    val avgCalories = entries.map { it.calories }.average()
                    val minCalories = entries.minOf { it.calories }
                    val maxCalories = entries.maxOf { it.calories }


                    withContext(Dispatchers.Main) {
                        binding.avgCaloriesText.text = "Average Calories: ${avgCalories.toInt()}"
                        binding.minCaloriesText.text = "Min Calories: $minCalories"
                        binding.maxCaloriesText.text = "Max Calories: $maxCalories"
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.avgCaloriesText.text = "No entries available"
                        binding.minCaloriesText.text = ""
                        binding.maxCaloriesText.text = ""
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
