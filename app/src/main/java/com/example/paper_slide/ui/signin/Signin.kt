package com.example.paper_slide.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivitySigninBinding
import com.example.paper_slide.ui.forgotpassword.ForgotPassword
import com.example.paper_slide.ui.signup.SignUpActivity
import com.example.paper_slide.util.SharedPref
import com.example.paper_slide.util.Validate
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

class Signin : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001
    private lateinit var binding:ActivitySigninBinding

    private var context=this@Signin
    private lateinit var callbackManager: CallbackManager
    private lateinit var signInViewModel: SignInViewModel
    private var TAG = "SignInLogs"

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
            .requestIdToken(getString(R.string.Android_client_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleSignin.setOnClickListener {
            signIn()
        }

        binding.facebookSignin.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this@Signin, mutableListOf("public_profile","user_phone_number"))

        }
    /*    LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    fetchFacebookData(loginResult.accessToken)
                    Log.d(TAG, "success: on success")
                 //   finish()
                }

                override fun onCancel() {
                    Log.d(TAG, "onCancel: on Cancel")
                }

                override fun onError(exception: FacebookException) {
                    Log.d(TAG, "onError: ${exception.message}")
                }
            })*/



    }
    private fun fetchFacebookData(accessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(
            accessToken
        ) { jsonObject, response ->
            // Handle the user information
            val userId = jsonObject?.getString("id")
            val userName = jsonObject?.getString("name")
          // val email = jsonObject?.getString("email")
           val phoneNumber = jsonObject?.getString("phone")
            // ... other fields
            // You can update UI, send data to a server, etc.
            Toast.makeText(this, userName +userId+phoneNumber, Toast.LENGTH_SHORT).show()
        }

        val parameters = Bundle()
        parameters.putString("fields", "id,name,phone")
        request.parameters = parameters
        request.executeAsync()
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
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == RC_SIGN_IN) {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
                val account: GoogleSignInAccount? = result?.signInAccount
                handleSignInResult(account)
            }
            //Toast.makeText(this, "invalid user", Toast.LENGTH_SHORT).show()
        }


        private fun handleSignInResult(account: GoogleSignInAccount?) {
            val sharedPref = SharedPref(this@Signin)
            if (account != null) {
                val displayName = account.displayName
                val email = account.email
                val photoUrl = account.photoUrl.toString()
                lifecycleScope.launch {
                    sharedPref.userPhoto = photoUrl
                    signInViewModel.validateSignIn(email!!,null,binding.progressBar )
                }
                Toast.makeText(this, "account succress", Toast.LENGTH_SHORT).show()

                Log.d(TAG, "handleSignInResult: $displayName $email $photoUrl")
            } else {
                Toast.makeText(this, "account is null", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "handleSignInResult: Account is Null")
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