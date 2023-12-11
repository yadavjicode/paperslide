package com.example.paper_slide.ui.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityHomeBinding
import com.example.paper_slide.ui.createpresentation.Presentation
import com.example.paper_slide.ui.ocr.Ocr
import com.example.paper_slide.ui.pastelink.PasteLink
import com.example.paper_slide.ui.signatureoptions.SignatureActivity
import com.example.paper_slide.ui.texteditor.TextEditor
import com.google.android.material.navigation.NavigationView
import com.example.paper_slide.ui.preview.Preview
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import java.io.IOException
import java.security.Signature

//import com.google.firebase.crashlytics.buildtools.reloc.javax.annotation.meta.When

class Home : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding  : ActivityHomeBinding
    private var context =this@Home
    var imageUrl: Uri? = null
    var textRecognizer: TextRecognizer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = DataBindingUtil.setContentView(context , R.layout.activity_home)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerlayout)
        val navview: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        intview()
       // binding.navigatDrawer

    }


    private fun intview(){
/*<<<<<<< HEAD
        binding.scan.setOnClickListener {
            val intent = Intent(this@Home, Ocr::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
=======*/

            binding.scan!!.setOnClickListener(View.OnClickListener {
                ImagePicker.with(this@Home)
                    .crop() //Crop image(Optional), Check Customization for more option
                    .compress(1024) //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(
                        1080,
                        1080
                    ) //Final image resolution will be less than 1080 x 1080(Optional)
                    .start()
            })


        binding.createSign.setOnClickListener {

            val intent = Intent(this@Home, SignatureActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)

        }
        binding.writeNotes.setOnClickListener {
            val intent = Intent(this@Home, TextEditor::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        binding.createPresentation.setOnClickListener {
            val intent = Intent(this@Home, Presentation::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        binding.pasteALink.setOnClickListener {
            val intent = Intent(this@Home, PasteLink::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        binding.navigatDrawer.setOnClickListener {
            val drawerLayout: DrawerLayout = findViewById(R.id.drawerlayout)
            drawerLayout.openDrawer(GravityCompat.START)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {

        }
        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (data != null) {
                imageUrl = data.data
                Toast.makeText(this, "image selected", Toast.LENGTH_SHORT).show()
                recognizeText()

            }
        } else {
            Toast.makeText(this, "image not select", Toast.LENGTH_SHORT).show()
        }
    }
    private fun recognizeText() {
        if (imageUrl != null) {
            try {
                val inputImage = InputImage.fromFilePath(this@Home, imageUrl!!)
                val result = textRecognizer!!.process(inputImage)
                    .addOnSuccessListener { text ->
                        val recognizeText = text.text
                        //  textview!!.setText(recognizeText)
                        startActivity(Intent(this@Home, Preview::class.java)

                            .putExtra("ocrtext",recognizeText.toString())

                        )


                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            this@Home,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}