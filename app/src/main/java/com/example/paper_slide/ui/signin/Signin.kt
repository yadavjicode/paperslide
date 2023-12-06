package com.example.paper_slide.ui.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivitySigninBinding
import com.example.paper_slide.ui.forgotpassword.ForgotPassword
import com.example.paper_slide.ui.signup.SignUpActivity
import com.example.paper_slide.util.Validate
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.coroutines.launch

class Signin : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private lateinit var binding:ActivitySigninBinding

    private var context=this@Signin
    private lateinit var callbackManager: CallbackManager
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

      binding =  DataBindingUtil.setContentView(context,R.layout.activity_signin)
        callbackManager =CallbackManager.Factory.create()

        initViews()

           signInViewModel = ViewModelProvider(
               context,
               SignInVMFactory(context)
           )[SignInViewModel::class.java]

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleSignin.setOnClickListener {
            signIn()
        }


    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun initViews() {
        binding.siSignin.setOnClickListener {
            validateview()
        }
        binding.btnforgot.setOnClickListener{

            signInViewModel.startNewActivity(ForgotPassword::class.java)
        }
        binding.btnSignup.setOnClickListener {

            signInViewModel.startNewActivity(SignUpActivity::class.java)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)

                // Signed in successfully, handle the account
                handleSignInResult(account)
            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                // Display an appropriate error message to the user.
                e.printStackTrace()
            }
        }
        Toast.makeText(this, "null user", Toast.LENGTH_SHORT).show()
    }

    private fun handleSignInResult(account: GoogleSignInAccount?) {
        if (account != null) {

            val displayName = account?.displayName
            val email = account?.email
            val photoUrl = account?.photoUrl
            Toast.makeText(this, "succrss", Toast.LENGTH_SHORT).show()
            // Use the user information as needed
           // Log.d("GoogleSignIn", "Display Name: $displayName, Email: $email, Photo URL: $photoUrl")
        } else {
            // Handle sign-in failure
           // Log.d("GoogleSignIn", "Sign-in failed")
          //  val status = account.status
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateview() {

        val email =binding.siEmail.text.toString()
        val password =binding.siPassword.text.toString()
        val validate =Validate()
        if(email.isEmpty()){
            binding.siEmail.error="Please Enter email"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.siEmail.error="Enter Valid Email Id"
        }else if(password.isEmpty() || password.length <= 8 ||!validate.validatePassword(password)){
            binding.siPassword.error="Enter valid password(password should contain lower case , upper case,numbers and symbols)"
        }else{
            lifecycleScope.launch {
                signInViewModel.validateSignIn(
                    email.toString(),
                    password.toString(),
                    binding.progressBar
                )
            }
        }

    }
}