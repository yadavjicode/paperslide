package com.example.paper_slide.ui.texteditor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.paper_slide.R;
import com.github.irshulx.Editor;
import com.github.irshulx.models.EditorTextStyle;

public class TextEditor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);

          val  editor = (Editor) findViewById(R.id.editor);
       // findViewById(R.id.action_h1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H1);
            }
        });


    }
}