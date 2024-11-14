package com.example.project6bitfit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project6bitfit.databinding.FragmentEntryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EntryFragment : Fragment(R.layout.fragment_entry) {
    private lateinit var adapter: FoodEntryAdapter
    private var _binding: FragmentEntryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerViewEntries
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = FoodEntryAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // Initialize the database and DAO
        val foodEntryDao = FoodDatabase.getDatabase(requireContext()).foodEntryDao()

        // Update RecyclerView with database changes
        lifecycleScope.launch {
            foodEntryDao.getAllEntries().collect { entries ->
                adapter.updateEntries(entries)
            }
        }

        // add new entry
        binding.newEntryBtn.setOnClickListener {
            val intent = Intent(requireContext(), AddEntryActivity::class.java)
            startActivity(intent)
        }

        // claer all entries
        binding.clearBtn.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    foodEntryDao.clearAllEntries()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
