package com.example.paper_slide.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityHomeBinding
import com.example.paper_slide.ui.ocr.Ocr
import com.google.android.material.navigation.NavigationView
//import com.google.firebase.crashlytics.buildtools.reloc.javax.annotation.meta.When

class Home : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding  : ActivityHomeBinding
    private var context =this@Home
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
        /*  navview.setNavigationItemSelectedListener {
        When(it.itemId){


        }
            true


        } */

       /* binding.scanImage.setOnClickListener {
            val intent = Intent(this@Home, Ocr::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }*/

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {

        }
        return true
    }
}