package com.example.paper_slide.ui.ocr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.paper_slide.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class Ocr extends AppCompatActivity {
ImageView camera,clearall, copy;
EditText textview;
Uri imageUrl;

TextRecognizer textRecognizer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

       //  camera =findViewById(R.id.ocrcamera);
       // clearall=findViewById(R.id.ocrclear_all);
        camera =findViewById(R.id.ocrcopy);
        textview=findViewById(R.id.ocrtextview);
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(Ocr.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK){
            if(data!=null){
                imageUrl =data.getData();
                Toast.makeText(this, "image select", Toast.LENGTH_SHORT).show();

                recognizeText();
            }


        }
       else{
            Toast.makeText(this, "image not select", Toast.LENGTH_SHORT).show();
        }

    }

    private void recognizeText() {

        if(imageUrl!=null){

            try {
                InputImage inputImage = InputImage.fromFilePath(Ocr.this, imageUrl);

                Task<Text> result =textRecognizer.process(inputImage)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text text) {
                                String recognizeText =text.getText();
                                textview.setText(recognizeText);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(Ocr.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            catch (IOException e){

                e.printStackTrace();
            }
        }
    }
}