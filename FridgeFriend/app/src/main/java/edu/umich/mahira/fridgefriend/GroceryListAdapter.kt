package edu.umich.mahira.fridgefriend

import android.content.Context
import android.content.Intent
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
            listItemView.root.setBackgroundColor(Color.parseColor(if (position % 2 == 0) "#FFFFFFFF" else "#F1FEFF"))

            listItemView.MinusButton.visibility = View.VISIBLE
            listItemView.MinusButton.setOnClickListener { v: View ->
                if (v.id == R.id.MinusButton) {
                    for (i in items) {
                        if (i != null) {
                            if (i.name == name) {
                                i.quantity = i.quantity?.minus(1)
                                quantity = i.quantity
                                listItemView.numbersItemTextView.text = quantity.toString()
                                if(i.quantity == 0){
                                    items.remove(i)
                                    // Refresh here

                                }
                                break
                            }
                        }
                    }

                }
            }
        }
        return listItemView.root
    }
}