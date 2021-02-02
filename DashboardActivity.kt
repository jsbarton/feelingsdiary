package com.example.feelingsdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
class DashboardActivity : AppCompatActivity(){
    var firstNum_1: EditText? = null


    var arrayList1 = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        firstNum_1 = findViewById(R.id.firstNum_1)
        //Initialize and define parameters for GraphView
        val graph = findViewById<View>(R.id.graph) as GraphView
        graph.viewport.setMinX(0.0)
        graph.viewport.setMinY(1.0)
        graph.viewport.setMaxX(30.0)
        graph.viewport.setMaxY(10.0)
        graph.gridLabelRenderer.horizontalAxisTitle = "Entry Number"
        graph.gridLabelRenderer.verticalAxisTitle = "Mood Rating"
        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.isYAxisBoundsManual = true
        val button = findViewById(R.id.addButton) as Button
        graph.visibility = View.VISIBLE


        //Source: https://codinginflow.com/tutorials/android/bottomnavigationview
        val bottomNav =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)



        //Upload button
        button.setOnClickListener {

            //Do not accept numbers less than 1, greater than 10. Generate Toast
            if (firstNum_1!!.text.toString().toDouble() > 10 || firstNum_1!!.text.toString()
                    .toDouble() < 0
            ) {
                Toast.makeText(this, "Number must be between 1 and 10", Toast.LENGTH_SHORT).show()
            } else {


                arrayList1.add(firstNum_1!!.text.toString())
                //Convert Arraylist to Array of DataPoints for GraphView
                try {
                    var arr = Array(arrayList1.size) { i ->
                        DataPoint(
                            i.toDouble(),
                            arrayList1.get(i).toDouble()
                        )
                    }

                    val series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>(arr)

                    graph.addSeries(series)
                    firstNum_1!!.setText("")
                } catch (e: IllegalArgumentException) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }

            }
        }
    }


    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            loadFragment(item.itemId)
            true
        }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        var fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.profile -> {
                val intent = Intent(this, ProfilePageActivity::class.java)
                startActivity(intent)
                Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
            }
            R.id.calendar -> {
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)
                Toast.makeText(getApplicationContext(), "Calendar", Toast.LENGTH_SHORT).show();
            }
            R.id.diary -> {
                val intent = Intent(this, DashboardActivity::class.java )
                startActivity(intent)
                Toast.makeText(getApplicationContext(), "Diary", Toast.LENGTH_SHORT).show();
            }
            else -> {
                null
            }
        }


    }



}