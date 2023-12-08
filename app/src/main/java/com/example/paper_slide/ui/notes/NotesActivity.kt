package com.example.paper_slide.ui.notes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.text.style.ParagraphStyle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.paper_slide.R
import com.example.paper_slide.ui.home.Home
import com.example.paper_slide.databinding.ActivityNotesBinding

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class NotesActivity : AppCompatActivity() {

    private lateinit var binding :ActivityNotesBinding
    private var context =this@NotesActivity
    private val handler = Handler()

    private lateinit var timeRunnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        binding =DataBindingUtil.setContentView(context,R.layout.activity_notes)



        binding.btnBack.setOnClickListener {
            finish()
        }

    /*    val calender =Calendar.getInstance().time
       val dateFormat =DateFormat.getDateInstance().format(calender)
        val timeFormat =DateFormat.getTimeInstance(DateFormat.SHORT).format(calender)
        val dateTextView=findViewById<TextView>(R.id.date)
        dateTextView.text =dateFormat
        timeTextView.text = timeFormat
        val currentDateAndTime = getCurrentDateAndTime()
         Display the result in a TextView (assuming you have a TextView with id "textView" in your layout)
       timeTextView.text = currentDateAndTime  */

        timeRunnable = object : Runnable {
            override fun run() {
                updateDateTime()
                handler.postDelayed(this, 1000) // Update every 1000 milliseconds (1 second)
            }
        }

        // Start updating time
        handler.post(timeRunnable)

        val userInput =binding.edittext.text.toString()


        binding.edittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this example
            }
            override fun afterTextChanged(s: Editable?) {
                // Counting the number of characters

                val charCount = s?.toString()?.replace(" ", "")?.length ?: 0
               // val charCount =userInput.length

                // Display the result in the TextView
                binding.character.text = "$charCount : characters "
            }


            //   binding.character.text =  charCount.toString()
        })
    }



    private fun updateDateTime() {
        // Get current date and time
        val currentDateTime = Date()

        // Format the date and timeyyyy-MM-dd
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
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

    override fun onBackPressed() {
        super.onBackPressed()
        // Explicitly navigate to the MainActivity (home screen)
        val intent = Intent(this, Home::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Optional: Call finish() to close the current activity if needed
    }
}
