package edu.umich.mahira.fridgefriend

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
            listItemView.itemTextView.setText(name)
            listItemView.numbersItemTextView.text = quantity.toString()
            listItemView.root.setBackgroundColor(Color.parseColor(if (position % 2 == 0) "#FFFFFFFF" else "#F1FEFF"))
            listItemView.MinusButton.setBackgroundColor(Color.parseColor(if (position % 2 == 0) "#FFFFFFFF" else "#F1FEFF"))
            listItemView.EditButton.setBackgroundColor(Color.parseColor(if (position % 2 == 0) "#FFFFFFFF" else "#F1FEFF"))
            listItemView.MinusButton.visibility = View.VISIBLE
            listItemView.EditButton.visibility = View.INVISIBLE

            listItemView.MinusButton.setOnClickListener { v: View ->
                if (v.id == R.id.MinusButton) {
                    FridgeItemStore.deleteItem(context.applicationContext!!, name!!) {
                        notifyDataSetChanged()
                    }
                }
            }

            listItemView.itemTextView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                listItemView.EditButton.visibility = View.VISIBLE
                MyFridgeFragment().onPause()
            }

            listItemView.EditButton.setOnClickListener { v: View ->
                if (v.id == R.id.EditButton) {
                    FridgeItemStore.updateItem(context.applicationContext!!, name!!, listItemView.itemTextView.text.toString()) {
                        notifyDataSetChanged()
                        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(listItemView.EditButton.windowToken, 0)
                        listItemView.EditButton.visibility = View.INVISIBLE
                        MyFridgeFragment().onResume()
                    }
                }
            }
        }
        return listItemView.root
    }

}