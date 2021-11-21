package edu.umich.mahira.fridgefriend

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.umich.mahira.fridgefriend.databinding.ActivitySpendingGraphBinding
import com.jjoe64.graphview.GraphView
import android.R
import com.jjoe64.graphview.series.DataPoint

import com.jjoe64.graphview.series.LineGraphSeries
import java.util.EnumSet.range


val receipts = arrayListOf<Int?>() //use this to the items
class SpendingGraphActivity : AppCompatActivity() {
    private lateinit var view: ActivitySpendingGraphBinding
    private lateinit var itemListAdapter: SpendingListAdapter
    var graphView: GraphView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = ActivitySpendingGraphBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)

        itemListAdapter = SpendingListAdapter(this, receipts)
        view.SpendingListView.setAdapter(itemListAdapter)

        // on below line we are initializing our graph view.
        // on below line we are initializing our graph view.
        graphView = view.idGraphView

        // on below line we are adding data to our graph view.
        var counter = 0.0
        val dataArray = arrayListOf<DataPoint>()
        for (i in  receipts){
            dataArray.add(DataPoint(counter, i?.toDouble()!!))
            counter += 1.0
        }
        // on below line we are adding data to our graph view.
        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            dataArray.toTypedArray()
        )

        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.

        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
        graphView!!.setTitle("My Graph View")

        // on below line we are setting
        // text color to our graph view.

        // on below line we are setting
        // text color to our graph view.
        graphView!!.setTitleColor(R.color.black)

        // on below line we are setting
        // our title text size.

        // on below line we are setting
        // our title text size.
        graphView!!.setTitleTextSize(18f)

        // on below line we are adding
        // data series to our graph view.
        graphView!!.addSeries(series);
    }
}