package com.example.paperslide.ui.signin


import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityLoginBinding

import com.example.paperslide.util.Validate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var context = this@Login
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var googlebutton: ImageView
    private var TAG = "loginlogs"
    // private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
     private var showOneTapUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_login)
        setContentView(R.layout.activity_login)
      //  emailFocusListener()
        initviews()
        googlebutton = findViewById(R.id.google_btn)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)
        val account: GoogleSignInAccount? = GoogleSignIn
            .getLastSignedInAccount(this)
        if (account != null) {
            gotoHome()
        }

        googlebutton.setOnClickListener {
            goToSignin()
        }


    }

    private fun initviews() {
       binding.signInBtn.setOnClickListener{

           validateviews()
       }
    }

    private fun validateviews() {
        val email=binding.getEmail.text.toString()
        val password =binding.getPassword.text.toString()
        val validate = Validate()
       if(email.isEmpty()){
            binding.getEmail.error="Please Enter email"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.getPassword.error="Enter Valid Email Id"
        }else if(password.isEmpty() || password.length <= 8 ||!validate.validatePassword(password)){
            binding.getPassword.error="Enter valid password(password should contain lower case , upper case,numbers and symbols)"
        }else{

        }


    }

    /*   private fun emailFocusListener() {
           binding.getEmail.setOnFocusChangeListener { _, focused ->

               if(!focused) {
                   binding.emailContainer.helperText = validEmail()

               }
           }
       }

       private fun validEmail(): String? {

             val emailText=binding.getEmail.text.toString()
           if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
               Toast.makeText(this, "invalid email", Toast.LENGTH_SHORT).show()
               return "invalid email address"
           }
           return null
           Toast.makeText(this, "valid", Toast.LENGTH_SHORT).show()
       }   */


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










