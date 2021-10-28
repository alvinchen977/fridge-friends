package edu.umich.mahira.fridgefriend

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            listItemView.root.setBackgroundColor(Color.parseColor(if (position % 2 == 0) "#E0E0E0" else "#EEEEEE"))
        }
        return listItemView.root
    }
}