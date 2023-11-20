package com.example.paper_slide.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityHomeBinding
import com.example.paper_slide.ui.ocr.Ocr
import com.example.paper_slide.ui.signin.Signin
import com.example.paper_slide.util.SharedPref
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

//import com.google.firebase.crashlytics.buildtools.reloc.javax.annotation.meta.When

class Home : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding  :ActivityHomeBinding
    private var context =this@Home
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedPref : SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        homeViewModel = ViewModelProvider(context,
            HomeVMFactory(applicationContext)
        )[HomeViewModel::class.java]



        binding =DataBindingUtil.setContentView(context , R.layout.activity_home)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerlayout)
        val navview: NavigationView = findViewById(R.id.nav_view)





        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
          navview.setNavigationItemSelectedListener {
        when(it.itemId){
            R.id.logout ->{
           logout()
            }
        }
            true


        }

        binding.scan.setOnClickListener {
            val intent =Intent(this@Home,Ocr::class.java)
            intent.flags =Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {




        }
        return true
    }
    fun logout(){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to Logout?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                run {
                    sharedPref.login = "no"
                    val intent = Intent(context, Signin::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                    finish()
                    Toast.makeText(
                        context,
                        "Logout SuccessFull",
                        Toast.LENGTH_SHORT
                    ).show()
                    lifecycleScope.launch {
                        homeViewModel.validateLogout()
                    }

                }
            }
            .setNegativeButton(
                "No"
            ) { dialog, _ -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }
}