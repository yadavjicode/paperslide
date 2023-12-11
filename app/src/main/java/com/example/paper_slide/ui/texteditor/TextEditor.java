package com.example.paper_slide.ui.texteditor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.paper_slide.R;
import com.example.paper_slide.model.NoteResponse;
import com.example.paper_slide.network.APIInterface;
import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;
import com.github.irshulx.models.EditorTextStyle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import retrofit2.Response;

public class TextEditor extends AppCompatActivity {

    Editor editor ;
    TextView character;
    EditText textdemo;
    private HorizontalScrollView scrollView;
    private HorizontalScrollView scrollView_color;
    private HorizontalScrollView scrollView_textstyle;
    private ImageView  heading;
    private ImageView  color;
    EditText text;
    private ImageView  textstyle;
    private Runnable timeRunnable;

    private ImageView saveIV;
    private EditText titleET;
    private final Handler handler = new Handler();
    String TAG = "editorLog";

    private String editorText="";
    private String titleNote="";
    private  TextEditorViewModel textEditorVM;

    private ImageView backButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);

        editor =  findViewById(R.id.editor);
        textdemo =findViewById(R.id.text_demo);
        character =findViewById(R.id.character_c);
        saveIV =findViewById(R.id.saveIV);
        titleET =findViewById(R.id.titleET);
        backButton = findViewById(R.id.btnBack);

        textEditorVM = new ViewModelProvider(
                this,
                new TextEditorVMFactory(getApplicationContext())
        ).get(TextEditorViewModel.class);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Display the result in the TextView

        findViewById(R.id.action_h1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H1);
            }
        });

        findViewById(R.id.action_h2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H2);
            }
        });
        findViewById(R.id.action_h3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.H3);
            }
        });


        findViewById(R.id.action_unorder_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertList(false);
                String htmlContent = editor.getContentAsHTML();
                Spanned spanned = Html.fromHtml(htmlContent);
                String plainText = spanned.toString();


                int charCount = plainText.replaceAll("\\s", "")
                        .replaceAll("•", "").length();
                character.setText( charCount + " : characters ");
            }
        });

        findViewById(R.id.action_order_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertList(true);
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.openImagePicker();
            }
        }

        );
        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertLink();
            }
        });

        findViewById(R.id.action_eraser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clearAllContents();
            }
        });


        findViewById(R.id.action_newline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.insertDivider();
            }
        });



        editor.setEditorListener(new EditorListener() {
            @Override
            public void onTextChanged(EditText editText, Editable text) {
                // Toast.makeText(EditorTestActivity.this, text, Toast.LENGTH_SHORT).show();
              //  textE = text.toString();

            }
            @Override
            public void onUpload(Bitmap image, String uuid) {

                //do your upload image operations here, once done, call onImageUploadComplete and pass the url and uuid as reference.
                editor.onImageUploadComplete("http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg",uuid);
                // editor.onImageUploadFailed(uuid);
            }

            @Override
            public View onRenderMacro(String name, Map<String, Object> props, int index) {
                return null;
            }
        });
         scrollView =findViewById(R.id.scrollView_heading);
         scrollView_color =findViewById(R.id.scrollView_color);
         color =findViewById(R.id.action_color);


           heading =findViewById(R.id.action_heding);

         heading.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         toggleScrollViewVisibility();
     }
 });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleScrollViewcolor();
            }
        });

        scrollView_textstyle =findViewById(R.id.scrollView_text_style);
        textstyle =findViewById(R.id.action_text_style);
        textstyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleScrollViewtextstyle();
            }
        });


        setcolor ();
        textStyle();
        liveTime();

        editor.setEditorListener(new EditorListener() {
            @Override
            public void onTextChanged(EditText editText, Editable text) {
                //Toast.makeText(TextEditor.this, text, Toast.LENGTH_SHORT).show();
                textdemo.setText(text);
            //    editorText=text.toString();


            }

            @Override
            public void onUpload(Bitmap image, String uuid) {

            }

            @Override
            public View onRenderMacro(String name, Map<String, Object> props, int index) {
                return null;
            }
        });


        textdemo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this example
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Counting the number of characters
                /*int charCount = s != null ? s.toString().replace(" ", "").length() : 0;

                // Display the result in the TextView
              character.setText(charCount + " : characters ");*/
                String htmlContent = editor.getContentAsHTML();
                Spanned spanned = Html.fromHtml(htmlContent);
                String plainText = spanned.toString();


                int charCount = plainText.replaceAll("\\s", "")
                        .replaceAll("•", "").length();
                character.setText( charCount + " : characters ");

            }
        });

        saveIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleNote=titleET.getText().toString();
                editorText=editor.getContentAsHTML();
                /*String htmlContent = editor.getContentAsHTML();
                Spanned spanned = Html.fromHtml(htmlContent);
                String plainText = spanned.toString();*/
                //Toast.makeText(TextEditor.this, editorText +""+titleNote, Toast.LENGTH_SHORT).show();
                Log.d(TAG, editorText);

                validateViews(editorText,titleNote);
            }
        });
    }

    private void validateViews(String editorText, String titleNote) {
        if((editorText != null && titleNote != null )){

            textEditorVM.validateNote(titleNote,editorText);

        }
    }


    private void toggleScrollViewcolor() {

        if (scrollView_color.getVisibility() == View.VISIBLE) {
            scrollView_color.setVisibility(View.GONE);

        } else {
            scrollView_color.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            scrollView_textstyle .setVisibility(View.GONE);
        }
    }

    private void toggleScrollViewVisibility() {
        if (scrollView.getVisibility() == View.VISIBLE) {
            scrollView.setVisibility(View.GONE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
            scrollView_color.setVisibility(View.GONE);
            scrollView_textstyle .setVisibility(View.GONE);
        }
    }

    private void toggleScrollViewtextstyle() {
        if (scrollView_textstyle .getVisibility() == View.VISIBLE) {
            scrollView_textstyle .setVisibility(View.GONE);
        } else {
            scrollView_textstyle .setVisibility(View.VISIBLE);
            scrollView_color.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
        }
    }

    public void setcolor (){

        findViewById(R.id.action_c1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextColor("#FFFFFFFF");
            }
        });
        findViewById(R.id.action_c2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextColor("#8BC34A");
            }
        });
        findViewById(R.id.action_c3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextColor("#FFEB3B");
            }
        });
        findViewById(R.id.action_c4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextColor("#131312");
            }
        });
        findViewById(R.id.action_c5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextColor("#FF5722");
            }
        });
        findViewById(R.id.action_c6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextColor("#E11C1C");
            }
        });
        findViewById(R.id.action_c7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextColor("#009688");
            }
        });
        findViewById(R.id.action_c8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextColor("#7C5A41");
            }
        });

    }

    public void liveTime(){

        timeRunnable = new Runnable() {
            @Override
            public void run() {
                updateDateTime();
                handler.postDelayed(this, 1000); // Update every 1000 milliseconds (1 second)
            }
        };
        handler.post(timeRunnable);

    }
    private void updateDateTime() {
        // Get current date and time
        Date currentDateTime = new Date();

        // Format the date and time
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDateTime = sdf.format(currentDateTime);

        // Display the result in a TextView
        TextView timeTextView = findViewById(R.id.time_t);
        timeTextView.setText(formattedDateTime);
    }
    protected void onDestroy() {
        super.onDestroy();
        // Stop updating time when the activity is destroyed
        handler.removeCallbacks(timeRunnable);
    }
    public void textStyle(){

        findViewById(R.id.action_text1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.BOLD);

            }
        });

        findViewById(R.id.action_text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.ITALIC);
            }
        });

        findViewById(R.id.action_text3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.INDENT);
            }
        });
        findViewById(R.id.action_text4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.OUTDENT);
            }
        });

        findViewById(R.id.action_text5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.updateTextStyle(EditorTextStyle.BLOCKQUOTE);
            }
        });



    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == editor.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
               editor.insertImage(bitmap);
                Toast.makeText(this, bitmap.toString(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "this is wrong image", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // editor.RestoreState();
        }
    }



    }

