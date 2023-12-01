package com.example.paper_slide.ui.uploadSignature

import android.Manifest
import android.content.Intent
import android.hardware.SensorPrivacyManager.Sensors.CAMERA
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityUploadSignatureBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class UploadSignature: AppCompatActivity() {
    private lateinit var binding: ActivityUploadSignatureBinding
    private lateinit var uploadSignatureViewModel: UploadSignatureViewModel
    private val  context = this@UploadSignature
    private lateinit var selectedImageUri: Uri
    private lateinit var partSign :String
    val TAG ="SigUpLog"
    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.sigIV.setImageURI(it)
        selectedImageUri=it!!
    }
 /*   private val loadImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                // The user has successfully picked an image
                val data: Intent? = result.data
                val selectedImageUri: Uri = data?.data ?: return@registerForActivityResult

                // Now you can use this URI to load and set the image
                loadImageFromUri(selectedImageUri)
            }
        }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,R.layout.activity_upload_signature )

        uploadSignatureViewModel = ViewModelProvider(context,
                                    UploadSignatureVMFactory(applicationContext)
                                    )[UploadSignatureViewModel::class.java]
/*
        imageUriString = intent.getStringExtra("imageUri")!!
*/     /* Dexter.withContext(this)
            // we will use with permission method since we are working with many permissions
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                // ensure you implement members of the object which is related to dexter third party library
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            //since we have over one activity result i.e.,Camera, Gallery, save camera image
                            //to a directory, we shall set a companion object below the class main activity.
                            startActivityForResult(intent, CAMERA)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    // It is the alert dialog that user will allow permissions
                   // showRationalDialogForPermissions()
                }

            }).onSameThread().check()

*/
        initViews()
    }

    private fun initViews() {
        binding.uploadSigIV.setOnClickListener {
           /* Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE // Assuming this is the equivalent for READ_MEDIA_IMAGES
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        Log.e(TAG, "checkPermission: 1.2 FALSE ")
                        if (report.areAllPermissionsGranted()) {
                            // isPermissionsGranted = true;
                            // Toast.makeText(this@SetVisitActivity, "All permissions are granted!", Toast.LENGTH_SHORT).show()
                        }

                        if (report.isAnyPermissionPermanentlyDenied) {
                            // isPermissionsGranted = false;
                            // showSettingsDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                })
                .withErrorListener { error: DexterError ->
                    Toast.makeText(this, "Error occurred! $error", Toast.LENGTH_SHORT).show()
                }
                .onSameThread()
                .check()

*/
            // openGallery()
            contract.launch("image/*")
        }

        binding.sigSaveBtn.setOnClickListener {
            uploadSig()
        }

    }

    private fun uploadSig(){
        val filesDir = applicationContext.filesDir
        val file = File(filesDir,"image.png")

        val inputStream = contentResolver.openInputStream(selectedImageUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("sign",file.name,requestBody)
        partSign= part.toString()
        if(part != null){

            lifecycleScope.launch {
                uploadSignatureViewModel.validateSignature(part)
            }
        }
    }

  /*  private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }*/

 /*   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            // Handle the selected image
            selectedImageUri = data.data
            val imageView = binding.sigIV
            Toast.makeText(context, "$selectedImageUri", Toast.LENGTH_SHORT).show()
            try {
                val inputStream = selectedImageUri?.let { contentResolver.openInputStream(it) }
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                imageView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
            }
        }
    }*/





  /*  private fun openGallery() {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            loadImageLauncher.launch(galleryIntent)

    }*/

 /*   private fun loadImageFromUri(uri: Uri) {
        // Load the image into an ImageView
        val imageView = binding.sigIV
        selectedImageUri = uri

        try {
            val inputStream = contentResolver.openInputStream(selectedImageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
        }
    }*/

}