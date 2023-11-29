package com.example.paper_slide.ui.texteditor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paper_slide.R;
import com.github.irshulx.Editor;
import com.github.irshulx.EditorListener;
import com.github.irshulx.models.EditorTextStyle;

import java.io.IOException;
import java.util.Map;

public class TextEditor extends AppCompatActivity {

    Editor editor ;
    private HorizontalScrollView scrollView;
    private HorizontalScrollView scrollView_color;
    private HorizontalScrollView scrollView_textstyle;
    private ImageView  heading;
    private ImageView  color;
    private ImageView  textstyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_editor);
       editor =  findViewById(R.id.editor);

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



        editor.setEditorListener(new EditorListener() {
            @Override
            public void onTextChanged(EditText editText, Editable text) {
                // Toast.makeText(EditorTestActivity.this, text, Toast.LENGTH_SHORT).show();
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

