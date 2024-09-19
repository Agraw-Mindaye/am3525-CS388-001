// MainActivity.kt
package com.example.project2wishlist

import WishlistAdapter
import WishlistItem
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project2wishlist.R

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var wishlistAdapter: WishlistAdapter
    private val wishlistItems = mutableListOf<WishlistItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        wishlistAdapter = WishlistAdapter(wishlistItems)
        recyclerView.adapter = wishlistAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up input fields and button
        val etItemName: EditText = findViewById(R.id.etItemName)
        val etItemPrice: EditText = findViewById(R.id.etItemPrice)
        val etItemUrl: EditText = findViewById(R.id.etItemUrl)
        val btnAddItem: Button = findViewById(R.id.btnAddItem)

        // Handle button click to add a new item
        btnAddItem.setOnClickListener {
            val name = etItemName.text.toString()
            val price = etItemPrice.text.toString()
            val url = etItemUrl.text.toString()

            if (name.isNotEmpty() && price.isNotEmpty() && url.isNotEmpty()) {
                val newItem = WishlistItem(name, price, url)
                wishlistItems.add(newItem)
                wishlistAdapter.notifyItemInserted(wishlistItems.size - 1)

                // Clear input fields
                etItemName.text.clear()
                etItemPrice.text.clear()
                etItemUrl.text.clear()
            }
        }
    }
}
