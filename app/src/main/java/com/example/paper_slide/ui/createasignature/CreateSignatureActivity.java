package com.example.paper_slide.ui.createasignature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.cloudinary.Url;
import com.example.paper_slide.R;
import com.example.paper_slide.databinding.ActivityCreateSignatureBinding;
import com.example.paper_slide.ui.uploadSignature.UploadSignature;

import java.io.ByteArrayOutputStream;

public class CreateSignatureActivity extends AppCompatActivity {
    ActivityCreateSignatureBinding mainbinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_signature);

        mainbinding =ActivityCreateSignatureBinding.inflate(getLayoutInflater());
        setContentView(mainbinding.getRoot());

        mainbinding.cancelSign.setOnClickListener(view -> {


            mainbinding.signatureView.clearCanvas();

        });


        mainbinding.acceptSign.setOnClickListener(view -> {



            Bitmap bitmap=mainbinding.signatureView.getSignatureBitmap();
            ByteArrayOutputStream bytes =new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),bitmap,"val",null);
         //   if(signBitmap!= null){

     //Uri imageuri = FileProvider.getUriForFile(this,"vall",bitmap);
            Uri uri =Uri.parse(path);
                Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(this,'makeText(CreateSignatureActivity, Bitmap, int)', Toast.LENGTH_SHORT).show();

            // Create an Intent to start the next activity
          /*  Intent intent = new Intent(this, UploadSignature.class);

            // Pass the image URI to the next activity
            intent.putExtra("imageUri", uri.toString());

            // Start the next activity
            startActivity(intent);*/

          //  }

        });



    }
}