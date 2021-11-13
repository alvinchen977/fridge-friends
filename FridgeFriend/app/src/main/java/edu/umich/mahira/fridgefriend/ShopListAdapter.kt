package edu.umich.mahira.fridgefriend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.umich.mahira.fridgefriend.ShopListAdapter.ShopViewHolder

class ShopListAdapter : ListAdapter<Shop, ShopViewHolder>(ItemComparator/*()*/) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        return ShopViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.item)
    }

    class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val shopItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            shopItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ShopViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ShopViewHolder(view)
            }
        }
    }

    // make sure to use this in a way that adds the amounts of the items together if they are the same
    /*class ItemComparator : DiffUtil.ItemCallback<Shop>() { instead of next two lines? */
    companion object {
        private val ItemComparator = object : DiffUtil.ItemCallback<Shop>() {
            override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
                return oldItem.item == newItem.item
            }
        }
    }
}