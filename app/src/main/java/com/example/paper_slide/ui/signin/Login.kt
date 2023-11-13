package com.example.paper_slide.ui.signin


import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityLoginBinding
import com.example.paper_slide.home.Home
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch


class Login : AppCompatActivity() {

    private lateinit var gso:GoogleSignInOptions
    private lateinit var gsc:GoogleSignInClient
    private lateinit var  googleSign: Button

    private var TAG = "loginlogs"
   // private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
   // private var showOneTapUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       googleSign=findViewById(R.id.google_btn)

        gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc=GoogleSignIn.getClient(this,gso)
        val account:GoogleSignInAccount?=GoogleSignIn
            .getLastSignedInAccount(this)

        if(account!=null){

            gotoHome()
        }

        googleSign.setOnClickListener {
            goToSignin()
        }


    }

    private fun goToSignin() {

        val signInIntent=gsc.signInIntent
        startActivityForResult(signInIntent,1000)
    }

    private fun gotoHome() {
        val intent =Intent(this,Profile::class.java)
       startActivity(intent)
        finish()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==1000){

            val task:Task<GoogleSignInAccount> =GoogleSignIn
                .getSignedInAccountFromIntent(data)


            try{

                task.getResult(ApiException::class.java)
                gotoHome()

            }
            catch (e:java.lang.Exception){

                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
   }
}








