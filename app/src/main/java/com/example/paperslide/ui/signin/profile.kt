package com.example.paperslide.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.paperslide.R
//import com.example.paper_slide.signin.Login
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class profile : AppCompatActivity() {
    private lateinit var gImage:ImageView
    private lateinit var gName:TextView
    private lateinit var gEmail:TextView
    private lateinit var gid:TextView
    private lateinit var  gsignout:Button
    private lateinit var gso:GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        gEmail=findViewById(R.id.g_mail)
        gid=findViewById(R.id.g_id)
        gImage=findViewById(R.id.g_image)
        gsignout=findViewById(R.id.g_button)

         gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)
        val account: GoogleSignInAccount?= GoogleSignIn
            .getLastSignedInAccount(this)

        if(account!=null){

            gEmail.text=account.email
            gid.text=account.id
           // Glide.With(applicationContext).load(account.photoUrl).into(gImage)


        }
        else{

            gosignout()
        }


        gsignout.setOnClickListener{

            gosignout()
        }

    }

    private fun gosignout() {
        gsc.signOut().addOnSuccessListener {

           startActivity(Intent(this, Login::class.java))

            finish()
        }

    }


}