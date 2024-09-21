import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project2wishlist.R

class WishlistAdapter(private val wishlist: List<WishlistItem>) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameTextView: TextView
        val priceTextView: TextView
        val urlTextView: TextView

        init {
            nameTextView = view.findViewById(R.id.ItemNameTv)
            priceTextView = view.findViewById(R.id.ItemPriceTv)
            urlTextView = view.findViewById(R.id.ItemUrlTv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return ViewHolder(view) // return holder instance
    }

    // populate data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = wishlist[position]
        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = currentItem.price
        holder.urlTextView.text = currentItem.url
    }

    override fun getItemCount(): Int {
        return wishlist.size
    }
}
