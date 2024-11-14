package com.example.project6bitfit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FoodEntryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize RecyclerView to display entries
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_entries)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = FoodEntryAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // initialize database and DAO
        val foodEntryDao = FoodDatabase.getDatabase(this).foodEntryDao()

        // update RecyclerView with chnages
        lifecycleScope.launch {
            foodEntryDao.getAllEntries().collect { entries ->
                adapter.updateEntries(entries)
            }
        }

        // add a new entry
        val newEntryButton = findViewById<Button>(R.id.new_entry_btn)
        newEntryButton.setOnClickListener {
            val intent = Intent(this, AddEntryActivity::class.java)
            startActivity(intent)
        }

        // clear entries
        val clearAllButton = findViewById<Button>(R.id.clear_btn)
        clearAllButton.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    foodEntryDao.clearAllEntries()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        // refresh and show new entry
        val foodEntryDao = FoodDatabase.getDatabase(this).foodEntryDao()
        lifecycleScope.launch {
            foodEntryDao.getAllEntries().collect { entries ->
                adapter.updateEntries(entries)
            }
        }
    }
}

