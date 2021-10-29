package edu.umich.mahira.fridgefriend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ShopListAdapter : ListAdapter<Shop, ShopListAdapter.ShopViewHolder>(ItemComparator()) {

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

    class ItemComparator : DiffUtil.ItemCallback<Shop>() {
        override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem.item == newItem.item
        }
    }
}