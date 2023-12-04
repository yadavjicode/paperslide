package com.example.paper_slide.ui.pastelink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.paper_slide.R

class PasteLink : AppCompatActivity() {

    private lateinit var editTextUrl: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paste_link)

        editTextUrl = findViewById(R.id.link)
        val buttonOpenLink: Button = findViewById(R.id.link_button)

        buttonOpenLink.setOnClickListener {
            openLink()
        }
    }

    private fun openLink() {
        val url = editTextUrl.text.toString().trim()

        if (url.isNotEmpty() && Patterns.WEB_URL.matcher(url).matches()) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                    Toast.makeText(this, "Opening link: $url", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "No app can handle this URL", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error opening link", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
        }
    }
}