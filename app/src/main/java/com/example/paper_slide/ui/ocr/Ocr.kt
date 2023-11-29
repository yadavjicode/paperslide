package com.example.paper_slide.ui.ocr

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.paper_slide.R
import com.example.paper_slide.ui.home.Home
import com.example.paper_slide.ui.preview.Preview
import com.example.paper_slide.ui.summarize.Summarize
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

class Ocr : AppCompatActivity() {
    var camerad: ImageView? = null
    var clearall: ImageView? = null
    var copy: ImageView? = null
    var textview: EditText? = null
    var imageUrl: Uri? = null
    var textRecognizer: TextRecognizer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocr)
        camerad = findViewById(R.id.ocrcopy)
      //  textview = findViewById(R.id.ocrtextview)
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        camerad!!.setOnClickListener(View.OnClickListener {
            ImagePicker.with(this@Ocr)
                .crop() //Crop image(Optional), Check Customization for more option
                .compress(1024) //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                ) //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (data != null) {
                imageUrl = data.data
                Toast.makeText(this, "Process.....", Toast.LENGTH_SHORT).show()
                recognizeText()

            }
        } else {
            Toast.makeText(this, "image not select", Toast.LENGTH_SHORT).show()
        }
    }

    private fun recognizeText() {
        if (imageUrl != null) {
            try {
                val inputImage = InputImage.fromFilePath(this@Ocr, imageUrl!!)
                val result = textRecognizer!!.process(inputImage)
                    .addOnSuccessListener { text ->
                        val recognizeText = text.text
                      //  textview!!.setText(recognizeText)




                        startActivity(Intent(this@Ocr, Preview::class.java)

                            .putExtra("ocrtext",recognizeText.toString())

                        )


                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            this@Ocr,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onBackPressed() {
        // Explicitly navigate to the MainActivity (home screen)
        val intent = Intent(this, Home::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Optional: Call finish() to close the current activity if needed
    }
}