package com.example.project2wishlist

import WishlistAdapter
import WishlistItem
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project2wishlist.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.Rv) // get RecyclerView
        val items = mutableListOf<WishlistItem>() // get the items
        val adapter = WishlistAdapter(items) // create adaptr

        recyclerView.adapter = adapter // attach adapter to recycler view
        recyclerView.layoutManager = LinearLayoutManager(this) // set layout manager to position items

        val itemName: EditText = findViewById(R.id.ItemNameEt)
        val itemPrice: EditText = findViewById(R.id.ItemPriceEt)
        val itemUrl: EditText = findViewById(R.id.ItemUrlEt)
        val addBtn: Button = findViewById(R.id.AddItemBtn)

        addBtn.setOnClickListener {
            val name = itemName.text.toString()
            val price = itemPrice.text.toString()
            val url = itemUrl.text.toString()

            if (name.isNotEmpty() && price.isNotEmpty() && url.isNotEmpty()) {
                val newItem = WishlistItem(name, price, url)
                items.add(newItem)
                adapter.notifyItemInserted(items.size - 1)

                // clear the input fields
                itemName.text.clear()
                itemPrice.text.clear()
                itemUrl.text.clear()
            }
        }
    }
}
