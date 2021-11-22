package edu.umich.mahira.fridgefriend

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import edu.umich.mahira.fridgefriend.databinding.ListitemFridgeBinding
import kotlinx.android.synthetic.main.fragment_my_fridge.*
import kotlinx.android.synthetic.main.fragment_my_fridge.view.*

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
            listItemView.MinusButton.visibility = View.VISIBLE
            listItemView.EditButton.visibility = View.VISIBLE

            listItemView.MinusButton.setOnClickListener { v: View ->
                if (v.id == R.id.MinusButton) {
                    val i = items[position]
                    if (i != null) {
                        if (i.name == name) {
                            i.quantity = i.quantity?.minus(1)
                            quantity = i.quantity
                            listItemView.numbersItemTextView.text = quantity.toString()
                            if(i.quantity == 0){
                                items.remove(i)
                                // Refresh fragment view
                                notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
            listItemView.EditButton.setOnClickListener { v: View ->
                if (v.id == R.id.EditButton) {
                    val i = items[position]
                    if (i != null) {
                        if (i.name == name && i.name != listItemView.itemTextView.text.toString()) {
                            //Check when you edit an item it is not in de list already
                            for(item in items){
                                if(listItemView.itemTextView.text.toString() == item?.name) {
                                    items[position]?.quantity = items[position]?.quantity?.plus(item.quantity!!)
                                    items.remove(item)
                                    break
                                }
                            }
                            i.name = listItemView.itemTextView.text.toString()
                            notifyDataSetChanged()
                            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(listItemView.EditButton.windowToken, 0)
                        }
                    }
                }
            }
        }
        return listItemView.root
    }

}