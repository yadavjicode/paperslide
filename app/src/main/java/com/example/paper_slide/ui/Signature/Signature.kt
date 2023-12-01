package com.example.paper_slide.ui.Signature

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivitySignatureBinding
import com.example.paper_slide.ui.createasignature.CreateSignatureActivity
import com.example.paper_slide.ui.home.Home
import com.example.paper_slide.ui.uploadSignature.UploadSignature
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

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
   /* private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var cropLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestCameraPermission: ActivityResultLauncher<String>*/




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =DataBindingUtil.setContentView(context,R.layout.activity_signature)
       // setContentView(R.layout.activity_signature)
        intview()
        // Initialize cameraLauncher
   /*     initCameraLauncher()

        // Initialize cropLauncher
        initCropLauncher()

        // Initialize requestCameraPermission
        requestCameraPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    // Camera permission granted, proceed with opening the camera
                    openCamera()
                } else {
                    // Camera permission denied, handle accordingly
                    // You may want to show a message or request the permission again
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
                }
            }*/
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

    private fun intview() {


        binding.importSign.setOnClickListener {
            val intent = Intent(this@Signature, UploadSignature::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
       /* binding.cameraIV.setOnClickListener {
            //requestCameraPermission()
        }*/
    }
    override fun onBackPressed() {
        super.onBackPressed()
        // Explicitly navigate to the MainActivity (home screen)
        val intent = Intent(this, Home::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Optional: Call finish() to close the current activity if needed
    }

/*    private fun initCameraLauncher() {
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // Camera activity result handling
                    val imageUri = result.data?.data
                    if (imageUri != null) {
                        // Image has been captured, start cropping
                        startCrop(imageUri)
                    }
                }
            }
    }

    private fun initCropLauncher() {
        cropLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // Crop activity result handling
                    val croppedImageUri = CropImage.getActivityResult(result.data)?.uri
                    if (croppedImageUri != null) {
                        // Image has been cropped, start the next activity
                        startNextActivity(croppedImageUri)
                    }
                }
            }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun startCrop(imageUri: Uri) {
        CropImage.activity(imageUri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1)  // Set your desired aspect ratio
            .setFixAspectRatio(true)  // Enable fixed aspect ratio
            .setMultiTouchEnabled(true)
            .start(this)
    }



    private fun requestCameraPermission() {
        if (hasCameraPermission()) {
            openCamera()
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }*/
private fun startNextActivity(croppedImageUri: Uri) {
    val intent = Intent(this, UploadSignature::class.java)
    intent.putExtra("CROPPED_IMAGE_URI", croppedImageUri.toString())
    startActivity(intent)
}
}


