package com.example.paper_slide.ui.signature

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivitySignatureBinding
import com.example.paper_slide.ui.home.Home
import com.example.paper_slide.ui.uploadSignature.UploadSignature
import com.theartofdev.edmodo.cropper.CropImage

class Signature : AppCompatActivity() {
    private var context =this@Signature
    private lateinit var binding :ActivitySignatureBinding
    private val cropActivityResultContract = object : ActivityResultContract<Any?,Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(16,9)
                .getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return  CropImage.getActivityResult(intent)?.uri
        }
    }
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>
    private val permissionId =14
    private val permissionNameList = if(Build.VERSION.SDK_INT >=33) {
        arrayListOf(
            android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )
    } else{
            arrayListOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(context,R.layout.activity_signature)
        checkMultiplePermission()
        intView()
    }

    private fun intView() {
        binding.importSign.setOnClickListener {
            val intent = Intent(this@Signature, UploadSignature::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract){
            it?.let { uri ->
                // Toast.makeText(context, "$uri", Toast.LENGTH_SHORT).show()
                if (uri != null) {
                    // Image has been cropped, start the next activity
                    startNextActivity(uri)
                }
            }
        }
        binding.cameraIV.setOnClickListener {
            cropActivityResultLauncher.launch(null)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        // Explicitly navigate to the MainActivity (home screen)
        val intent = Intent(this, Home::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Optional: Call finish() to close the current activity if needed
    }

private fun startNextActivity(croppedImageUri: Uri) {
    val intent = Intent(this, UploadSignature::class.java)
    intent.putExtra("CROPPED_IMAGE_URI", croppedImageUri.toString())
    startActivity(intent)
}

    private fun checkMultiplePermission(): Boolean {
        val listPermissionNeeded = arrayListOf<String>()
        for (permission in permissionNameList) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission) != PackageManager.PERMISSION_GRANTED
                        ) {
                            listPermissionNeeded.add(permission)
                        }
                    }
                    if (listPermissionNeeded.isNotEmpty()) {
                        ActivityCompat.requestPermissions(
                            this,
                            listPermissionNeeded.toTypedArray(),
                            permissionId
                        )
                        return false
                    }
        return true
    }
}


