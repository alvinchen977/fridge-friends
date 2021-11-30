package edu.umich.mahira.fridgefriend

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import edu.umich.mahira.fridgefriend.databinding.ActivitySpendingListAdapterBinding
import edu.umich.mahira.fridgefriend.databinding.ListitemFridgeBinding

class SpendingListAdapter(context: Context, users: ArrayList<Int?>) :
    ArrayAdapter<Int?>(context, 0, users.reversed()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItemView = (convertView?.tag /* reuse binding */ ?: run {
            val rowView = LayoutInflater.from(context).inflate(R.layout.activity_spending_list_adapter, parent, false)
            rowView.tag = ActivitySpendingListAdapterBinding.bind(rowView) // cache binding
            rowView.tag
        }) as ActivitySpendingListAdapterBinding

        getItem(position)?.run {
            listItemView.spendingTextView.text = this.toString()
            listItemView.root.setBackgroundColor(Color.parseColor(if (position % 2 == 0) "#F1FEFF" else "#FFFFFFFF"))
            listItemView.spendingTextView.setBackgroundColor(Color.parseColor(if (position % 2 == 0) "#F1FEFF" else "#FFFFFFFF"))
        }
        notifyDataSetChanged()
        return listItemView.root
    }
}