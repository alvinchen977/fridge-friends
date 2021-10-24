package edu.umich.mahira.fridgefriend

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import edu.umich.mahira.fridgefriend.databinding.ListitemFridgeBinding

class GroceryListAdapter(context: Context, users: ArrayList<Item?>) :
    ArrayAdapter<Item?>(context, 0, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItemView = (convertView?.tag /* reuse binding */ ?: run {
            val rowView = LayoutInflater.from(context).inflate(R.layout.listitem_fridge, parent, false)
            rowView.tag = ListitemFridgeBinding.bind(rowView) // cache binding
            rowView.tag
        }) as ListitemFridgeBinding

        getItem(position)?.run {
            listItemView.itemTextView.text = name
            listItemView.numbersItemTextView.text = quantity.toString()
            listItemView.root.setBackgroundColor(Color.parseColor(if (position % 2 == 0) "#E0E0E0" else "#EEEEEE"))
        }

        return listItemView.root
    }
}