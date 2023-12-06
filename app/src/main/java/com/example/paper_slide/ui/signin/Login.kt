package com.example.paper_slide.ui.signin


import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.util.Log
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityLoginBinding
import com.example.paper_slide.util.SharedPref

import com.example.paper_slide.util.Validate
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch


class Login : AppCompatActivity() {
    private val RC_SIGN_IN = 9001
    private lateinit var binding: ActivityLoginBinding
    private var context = this@Login
    private lateinit var callbackManager: CallbackManager
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var googlebutton: ImageView
    private lateinit var googleSignInClient: GoogleSignInClient
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



       googlebutton.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile() // Add this line to request profile information
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
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
       // callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
   }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
            // updateUI(null)  // You can handle the failure as needed.
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            // You can access user information here
            val userId = account.id
            val displayName = account.displayName
            val email = account.email
            val photoUrl = account.photoUrl

            // Use this information as needed
            Toast.makeText(
                this,
                "Welcome, $displayName! ($email)",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}










