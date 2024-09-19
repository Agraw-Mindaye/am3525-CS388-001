// WishlistAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project2wishlist.R

class WishlistAdapter(private val wishlist: List<WishlistItem>) :
    RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tvItemName)
        val priceTextView: TextView = view.findViewById(R.id.tvItemPrice)
        val urlTextView: TextView = view.findViewById(R.id.tvItemUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wishlist, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val currentItem = wishlist[position]
        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = currentItem.price
        holder.urlTextView.text = currentItem.url
    }

    override fun getItemCount(): Int = wishlist.size
}
