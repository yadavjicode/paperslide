package com.example.paper_slide.signin

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
const val RC_SIGN_IN = 123
class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
   //private lateinit var callbackManager: CallbackManager
   // private var context = this@Login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        binding.googleBtn.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile() // Add this line to request profile information
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       // callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            val account: GoogleSignInAccount? = result?.signInAccount
           // handleSignInResult(account)
        }
    }

  /*  private fun handleSignInResult(account: GoogleSignInAccount?) {
        val sharedPref = SharedPref(this@Login)
        if (account != null) {
            val displayName = account.displayName
            val email = account.email
            val photoUrl = account.photoUrl.toString()
            lifecycleScope.launch {
                sharedPref.userPhoto = photoUrl
                signInViewModel.validateSignIn(email!!,null,binding.progressBar )
            }

            Log.d(TAG, "handleSignInResult: $displayName $email $photoUrl")
        } else {
            Log.d(TAG, "handleSignInResult: Account is Null")
        }
    }    */


}