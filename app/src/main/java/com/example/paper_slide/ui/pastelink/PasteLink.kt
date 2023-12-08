package com.example.paper_slide.ui.pastelink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityPasteLinkBinding
import com.example.paper_slide.databinding.ActivityPresentationBinding
import com.example.paper_slide.ui.home.Home
import com.example.paper_slide.ui.preview.Preview
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException




class PasteLink : AppCompatActivity() {
    private lateinit var binding: ActivityPasteLinkBinding
    var textRecognizer: TextRecognizer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paste_link)

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        binding.linkButton.setOnClickListener {
            // Assuming you have an EditText for pasting the link
            val linkEditText: EditText = findViewById(R.id.link)
            val link = linkEditText.text.toString().trim()

            if (link.isNotEmpty()) {
                // Assuming link is a valid URL
                val imageUrl = Uri.parse(link)
                Toast.makeText(this, imageUrl.toString(), Toast.LENGTH_SHORT).show()
                // Crop the image after loading from the link
                cropImage(imageUrl)
            } else {
                // If the link is empty, open the gallery to choose an image
                ImagePicker.with(this)
                    .crop() //Crop image(Optional), Check Customization for more options
                    .compress(1024) //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(
                        1080,
                        1080
                    ) //Final image resolution will be less than 1080 x 1080(Optional)
                    .start()
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun cropImage(imageUri: Uri) {
        // Perform the cropping logic here
        // Use an image cropping library or implement your own cropping mechanism
        // After cropping, proceed with the text recognition
        recognizeText(imageUri)
    }

    private fun recognizeText(imageUri: Uri) {
        try {
            val inputImage = InputImage.fromFilePath(this, imageUri)
            val result = textRecognizer!!.process(inputImage)
                .addOnSuccessListener { text ->
                    val recognizeText = text.text
                    startActivity(
                        Intent(this, Preview::class.java)
                            .putExtra("ocrtext", recognizeText.toString())
                    )
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    }
