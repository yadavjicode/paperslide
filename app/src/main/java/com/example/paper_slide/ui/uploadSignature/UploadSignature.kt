package com.example.paper_slide.ui.uploadSignature

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityUploadSignatureBinding
import kotlinx.coroutines.launch

class UploadSignature: AppCompatActivity() {
    private lateinit var binding: ActivityUploadSignatureBinding
    private lateinit var uploadSignatureViewModel: UploadSignatureViewModel
    private val  context = this@UploadSignature
    private var selectedImageUri: Uri? = null
    private val loadImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                // The user has successfully picked an image
                val data: Intent? = result.data
                val selectedImageUri: Uri = data?.data ?: return@registerForActivityResult

                // Now you can use this URI to load and set the image
                loadImageFromUri(selectedImageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,R.layout.activity_upload_signature )

        uploadSignatureViewModel = ViewModelProvider(context,
                                    UploadSignatureVMFactory(applicationContext)
                                    )[UploadSignatureViewModel::class.java]
/*
        imageUriString = intent.getStringExtra("imageUri")!!
*/

        initViews()
    }

    private fun initViews() {


        binding.uploadSigIV.setOnClickListener {
            openGallery()
        }

        binding.sigSaveBtn.setOnClickListener {
            uploadSig()
        }

    }

    private fun uploadSig(){
        if(selectedImageUri != null){

            lifecycleScope.launch {
                uploadSignatureViewModel.validateSignature(selectedImageUri!!)
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





    private fun openGallery() {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            loadImageLauncher.launch(galleryIntent)

    }

    private fun loadImageFromUri(uri: Uri) {
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
    }

}