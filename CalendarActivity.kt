package com.example.feelingsdiary;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class CalendarActivity : AppCompatActivity() {

    var calender: CalendarView? = null
    var date: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setting Calendar as the view
        setContentView(R.layout.activity_calendar)

        calender = findViewById<View>(R.id.calender) as CalendarView
        date = findViewById<View>(R.id.date_view) as TextView

        // Source: https://www.geeksforgeeks.org/android-creating-a-calendar-view-app/
        calender!!.setOnDateChangeListener(
            OnDateChangeListener { view, year, month, dayOfMonth ->
                val m = month + 1;
                val dateS = ( dayOfMonth.toString() + "-" + m + "-" + year)
                date!!.text = dateS
            })
        // Source: https://codinginflow.com/tutorials/android/bottomnavigationview
        val bottomNav =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    // source: https://www.tutorialspoint.com/how-to-add-calendar-events-in-android-app
    // Adding events to the calendar using Google Calendar
    fun AddCalendarEvent(view: View?) {
        val event = Calendar.getInstance()
        val i = Intent(Intent.ACTION_EDIT)
        i.type = "vnd.android.cursor.item/event"
        i.putExtra("startTime", event.timeInMillis)
        i.putExtra("endTime", event.timeInMillis + 60 * 60 * 1000)
        i.putExtra("eventName", "Calendar Event")
        startActivity(i)
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
                val intent = Intent(this,DashboardActivity::class.java )
                startActivity(intent)
                Toast.makeText(getApplicationContext(), "Diary", Toast.LENGTH_SHORT).show();
            }
            else -> {
                null
            }
        }

    }

}
