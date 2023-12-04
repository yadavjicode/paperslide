package com.example.paper_slide.ui.createasignature

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityCreateSignatureBinding
import java.io.ByteArrayOutputStream

class CreateSignatureActivity : AppCompatActivity() {
    var mainbinding: ActivityCreateSignatureBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_signature)
        mainbinding = ActivityCreateSignatureBinding.inflate(
            layoutInflater
        )
        setContentView(mainbinding!!.root)
        mainbinding!!.cancelSign.setOnClickListener { view: View? -> mainbinding!!.signatureView.clearCanvas() }
        mainbinding!!.acceptSign.setOnClickListener { view: View? ->
            val bitmap = mainbinding!!.signatureView.signatureBitmap
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(
                applicationContext.contentResolver,
                bitmap,
                "val",
                null
            )
            //   if(signBitmap!= null){

            //Uri imageuri = FileProvider.getUriForFile(this,"vall",bitmap);
            val uri = Uri.parse(path)
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}