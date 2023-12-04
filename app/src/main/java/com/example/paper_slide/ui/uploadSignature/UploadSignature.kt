package com.example.paper_slide.ui.uploadSignature
import android.adservices.common.AdSelectionSignals.EMPTY
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityUploadSignatureBinding
import com.example.paper_slide.ui.Signature.Signature
import com.example.paper_slide.ui.createasignature.CreateSignatureActivity
import com.example.paper_slide.ui.home.Home
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.internal.EMPTY_BYTE_ARRAY
import java.io.File
import java.io.FileOutputStream

class UploadSignature: AppCompatActivity() {
    private lateinit var binding: ActivityUploadSignatureBinding
    private lateinit var uploadSignatureViewModel: UploadSignatureViewModel
    private val  context = this@UploadSignature
    private lateinit var selectedImageUri: Uri
    private lateinit var partSign :String
    // Retrieve the cropped image URI from the intent

    val TAG ="SigUpLog"
    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.sigIV.setImageURI(it)
        selectedImageUri=it!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,R.layout.activity_upload_signature )

        uploadSignatureViewModel = ViewModelProvider(context,
                                    UploadSignatureVMFactory(applicationContext)
                                    )[UploadSignatureViewModel::class.java]


        initViews()
    }

    private fun initViews() {
        val croppedImageUriString = intent.getStringExtra("CROPPED_IMAGE_URI")
        val sigImgUri = intent.getStringExtra("SIG_IMAGE_URI")

        if (croppedImageUriString != null){
            val croppedImageUri = Uri.parse(croppedImageUriString)
            binding.sigIV.setImageURI(croppedImageUri)
            selectedImageUri=croppedImageUri
        }

        if (sigImgUri != null){
            val croppedImageUri = Uri.parse(sigImgUri)
            binding.sigIV.setImageURI(croppedImageUri)
            selectedImageUri=croppedImageUri
        }

        binding.upSignatureCL.setOnClickListener {
            contract.launch("image/*")
        }
        binding.sigSaveBtn.setOnClickListener {
                uploadSig()
        }
    }

    private fun uploadSig(){
        val filesDir = applicationContext.filesDir
        val file = File(filesDir,"image.png")

        if(::selectedImageUri.isInitialized ) {
            val inputStream = contentResolver.openInputStream(selectedImageUri)

            val outputStream = FileOutputStream(file)
            inputStream!!.copyTo(outputStream)

            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("sign", file.name, requestBody)
            partSign = part.toString()
            if (part != null) {
                lifecycleScope.launch {
                    uploadSignatureViewModel.validateSignature(part)
                }
            }
        }else{
            Toast.makeText(context, "please select signature", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, Signature::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Optional: Call finish() to close the current activity if needed
    }
}