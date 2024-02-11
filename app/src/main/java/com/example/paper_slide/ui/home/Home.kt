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
import androidx.lifecycle.ViewModelProvider
import com.example.paper_slide.R
import com.example.paper_slide.ui.tc.TCActivity
import com.example.paper_slide.databinding.ActivityHomeBinding
import com.example.paper_slide.ui.createpresentation.Presentation
import com.example.paper_slide.ui.ocr.Ocr
import com.example.paper_slide.ui.pastelink.PasteLink
import com.example.paper_slide.ui.policy.PolicyActivity
import com.example.paper_slide.ui.signatureoptions.SignatureActivity
import com.example.paper_slide.ui.texteditor.TextEditor
import com.google.android.material.navigation.NavigationView
import com.example.paper_slide.ui.preview.Preview
import com.example.paper_slide.ui.texteditor2.TextEditor2
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import java.io.IOException
class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,LogoutConfirmationDialogFragment.LogoutConfirmationListener
{
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding  : ActivityHomeBinding
    private var context =this@Home
    var imageUrl: Uri? = null
    var textRecognizer: TextRecognizer? = null
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = DataBindingUtil.setContentView(context , R.layout.activity_home)
        homeViewModel = ViewModelProvider(context,
            HomeVMFactory(applicationContext)
        )[HomeViewModel::class.java]

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerlayout)
        val navview: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        intview()
        navview.setNavigationItemSelectedListener(this)
    }

    private fun intview(){


            binding.scan!!.setOnClickListener {
                val intent = Intent(this@Home, Ocr::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }


        binding.createSign.setOnClickListener {

            val intent = Intent(this@Home, SignatureActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)

        }
        binding.writeNotes.setOnClickListener {
            val intent = Intent(this@Home, TextEditor2::class.java)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.nav_home) {
            Toast.makeText(context, "home", Toast.LENGTH_SHORT).show()

        }else if (id == R.id.logout){
            showLogoutConfirmationDialog()
        } else if (id == R.id.T_C){
            val intent = Intent(this@Home, TCActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        } else if (id == R.id.policy){
            val intent = Intent(this@Home, PolicyActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        return true
    }

    private fun showLogoutConfirmationDialog() {
        val dialogFragment = LogoutConfirmationDialogFragment()
        dialogFragment.show(supportFragmentManager, "LogoutConfirmationDialog")
    }


    override fun onLogoutConfirmed() {
        homeViewModel.validateLogout(this@Home)
    }

    override fun onLogoutCancelled() {
       // Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
    }


}