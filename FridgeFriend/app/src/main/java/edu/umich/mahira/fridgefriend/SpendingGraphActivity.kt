package edu.umich.mahira.fridgefriend

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.umich.mahira.fridgefriend.databinding.ActivitySpendingGraphBinding

val receipts = arrayListOf<Int?>() //use this to the items
class SpendingGraphActivity : AppCompatActivity() {
    private lateinit var view: ActivitySpendingGraphBinding
    private lateinit var itemListAdapter: SpendingListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivitySpendingGraphBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)

        itemListAdapter = SpendingListAdapter(this, receipts)
        view.SpendingListView.setAdapter(itemListAdapter)
    }
}