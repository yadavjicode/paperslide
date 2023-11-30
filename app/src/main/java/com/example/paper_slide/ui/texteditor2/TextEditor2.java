package com.example.paper_slide.ui.texteditor2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paper_slide.R;

import java.io.File;
import java.io.IOException;

import jp.wasabeef.richeditor.RichEditor;

public class TextEditor2 extends AppCompatActivity {
    private RichEditor mEditor;
    private TextView mPreview;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_editor2);
        mEditor = findViewById(R.id.editor2);
       mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.RED);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("Insert text here...");








        findViewById(R.id.action_insert_image2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to pick an image from the gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        findViewById(R.id.action_newline2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mEditor.insertImage("/storage/emulated/0/Pictures/Imp Document/20231016_074715.jpg",
                        "dachshund", 320);

            }
        });



    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            // Get the actual path of the image file
            String imagePath = getImagePath(selectedImageUri);

            // Insert the image into RichEditor
            if (imagePath != null) {
                mEditor.insertImage(Uri.fromFile(new File(imagePath)).toString(), "Image Description" +imagePath, 320);
                Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imagePath = cursor.getString(column_index);
            cursor.close();
            return imagePath;
        }

        return uri.getPath(); // Fallback to URI.getPath() if cursor is null
    }






}