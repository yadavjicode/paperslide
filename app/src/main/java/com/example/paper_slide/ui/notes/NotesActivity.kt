package com.example.paper_slide.ui.notes

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.paper_slide.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NotesActivity : AppCompatActivity() {

    private val handler = Handler()
    private lateinit var timeRunnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
    //    val calender =Calendar.getInstance().time
     //   val dateFormat =DateFormat.getDateInstance().format(calender)
      //  val timeFormat =DateFormat.getTimeInstance(DateFormat.SHORT).format(calender)
      //  val dateTextView=findViewById<TextView>(R.id.date)

      //  dateTextView.text =dateFormat
       // timeTextView.text = timeFormat

       // val currentDateAndTime = getCurrentDateAndTime()

        // Display the result in a TextView (assuming you have a TextView with id "textView" in your layout)
       // timeTextView.text = currentDateAndTime
        timeRunnable = object : Runnable {
            override fun run() {
                updateDateTime()
                handler.postDelayed(this, 1000) // Update every 1000 milliseconds (1 second)
            }
        }

        // Start updating time
        handler.post(timeRunnable)
    }
    private fun updateDateTime() {
        // Get current date and time
        val currentDateTime = Date()

        // Format the date and time
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDateTime = sdf.format(currentDateTime)

        // Display the result in a TextView
        val timeTextView =findViewById<TextView>(R.id.time)
        timeTextView.text = formattedDateTime
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop updating time when the activity is destroyed
        handler.removeCallbacks(timeRunnable)
    }
}
