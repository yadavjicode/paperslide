
package com.example.paper_slide.ui.texteditor2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;



import com.example.paper_slide.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.richeditor.RichEditor;

public class TextEditor2 extends AppCompatActivity {
    private RichEditor mEditor;
    private TextView mPreview;
    private static final String TAG = "VideoActivityLogs";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int EDITOR_REQUEST_CODE = 123;
    private static final int VIDEO_REQ = 1;
    private Uri selectedImageUri;
    private HorizontalScrollView scrollView;
    private HorizontalScrollView scrollView_textbg;
    private HorizontalScrollView scrollView_color;
    private HorizontalScrollView scrollView_textstyle;
    private ImageView heading;
    private ImageView  color;
    private ImageView  textstyle;
    private ImageView  textbackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_editor2);
        mEditor = findViewById(R.id.editor2);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("Insert text here...");


        initConfig();
        initfeature();
        initcolor();
        textbackground();
        initheading();
        initextstyle();

        mPreview = (TextView) findViewById(R.id.preview);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });

        scrollView =findViewById(R.id.scrollView_heading);
        scrollView_color =findViewById(R.id.scrollView_color);
        color =findViewById(R.id.action_color2);


        heading =findViewById(R.id.action_heding_2);

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
        textstyle =findViewById(R.id.action_text_style2);
        textstyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleScrollViewtextstyle();
            }
        });

        scrollView_textbg =findViewById(R.id.scrollView_textbg_color);
        textbackground =findViewById(R.id.text_bg);
        textbackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleScrollViewtextbackground();
            }
        });

    }

    private void toggleScrollViewtextbackground() {

        if (scrollView_textbg .getVisibility() == View.VISIBLE) {
            scrollView_textbg .setVisibility(View.GONE);

        } else {
            scrollView_textbg .setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            scrollView_textstyle .setVisibility(View.GONE);
            scrollView_color.setVisibility(View.GONE);
        }
    }

    private void toggleScrollViewcolor() {

        if (scrollView_color.getVisibility() == View.VISIBLE) {
            scrollView_color.setVisibility(View.GONE);

        } else {
            scrollView_color.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            scrollView_textstyle .setVisibility(View.GONE);
            scrollView_textbg .setVisibility(View.GONE);
        }
    }

    private void toggleScrollViewVisibility() {
        if (scrollView.getVisibility() == View.VISIBLE) {
            scrollView.setVisibility(View.GONE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
            scrollView_color.setVisibility(View.GONE);
            scrollView_textstyle .setVisibility(View.GONE);
            scrollView_textbg .setVisibility(View.GONE);
        }
    }

    private void toggleScrollViewtextstyle() {
        if (scrollView_textstyle .getVisibility() == View.VISIBLE) {
            scrollView_textstyle .setVisibility(View.GONE);
        } else {
            scrollView_textstyle .setVisibility(View.VISIBLE);
            scrollView_color.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
            scrollView_textbg .setVisibility(View.GONE);
        }
    }


    private void initConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "du7ulzgcl");
        config.put("api_key", "124844432943985");
        config.put("api_secret", "sdCigQezOpsWQJqkKCd3G_kLdqs");
        MediaManager.init(this, config);
    }


    private void initfeature(){
        findViewById(R.id.action_insert_image2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to pick an image from the gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });

        findViewById(R.id.action_unorder_list2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBullets();
            }
        });
        findViewById(R.id.action_order_list2).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                mEditor.setNumbers();

            }

        });

        findViewById(R.id.undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.redo();
            }
        });


        findViewById(R.id.bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.subscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.superscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });
        findViewById(R.id.underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.text_bg).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });
        findViewById(R.id.indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.justify_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.justify_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.justify_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });


        findViewById(R.id.blockquote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        findViewById(R.id.youtube).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertYoutubeVideo("https://www.youtube.com/embed/pS5peqApgUA");
            }
        });

        findViewById(R.id.music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertAudio("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_5MG.mp3");
            }
        });
        findViewById(R.id.action_newline2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertVideo("https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_10MB.mp4", 360);
            }
        });

        findViewById(R.id.insert_link2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        findViewById(R.id.action_eraser2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
        findViewById(R.id.action_bg).setOnClickListener(new View.OnClickListener() {
            @Override



            public void onClick(View v) {
                mEditor.setEditorBackgroundColor(Color.BLUE);

            }
        });
    }

    private void initcolor() {

        findViewById(R.id.action_white).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.WHITE);
                isChanged = !isChanged;
            }
        });
        findViewById(R.id.action_green).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.GREEN);
                isChanged = !isChanged;
            }
        });
        findViewById(R.id.action_yellow).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.YELLOW);
                isChanged = !isChanged;
            }
        });
        findViewById(R.id.action_black).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.BLACK);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_orange).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.GRAY);
                isChanged = !isChanged;
            }
        });
        findViewById(R.id.action_red).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });
        findViewById(R.id.action_skyblue).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.rgb(135,206,235));
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_brown).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.rgb(150,75,0));
                isChanged = !isChanged;
            }
        });

    }

    private void textbackground(){
        findViewById(R.id.text_bg_white).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.WHITE);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.text_bg_black).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.BLACK);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.text_bg_brown).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.rgb(150,75,0));
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.text_bg_green).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.GREEN);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.text_bg_yellow).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.text_bg_skyblue).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.rgb(135,206,235));
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.text_bg_skyblue).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.rgb(135,206,235));
                isChanged = !isChanged;
            }
        });


        findViewById(R.id.text_bg_orange).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.GRAY);
                isChanged = !isChanged;
            }
        });


    }


    private void initheading() {

        findViewById(R.id.action_head1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });
        findViewById(R.id.action_head2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });
        findViewById(R.id.action_head3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });
        findViewById(R.id.action_head4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });
        findViewById(R.id.action_head5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });
        findViewById(R.id.action_head6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });





    }

    private void initextstyle() {

        findViewById(R.id.italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setItalic();
            }
        });







    }



    private void initCo() {
        if (selectedImageUri != null) {
            Toast.makeText(TextEditor2.this, "image selected"+selectedImageUri, Toast.LENGTH_SHORT).show();
                MediaManager.get().upload(selectedImageUri)
                .option("resource_type", "image")
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        Log.d(TAG, "onStart: started " + requestId);
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        Log.d(TAG, "onProgress: uploading " + requestId);
                    }

                    @Override
                    public void onSuccess(String requestId, @NonNull Map resultData) {
                        String url = (String) resultData.get("secure_url");
                        Log.d(TAG, "onSuccess: unSuccess " + url);
                        if (url != null) {
                            mEditor.insertImage(url, "Image Description" +url, 320);
                         //   Toast.makeText(this, url.toString(), Toast.LENGTH_SHORT).show();
                        }
                        runOnUiThread(() ->
                                Toast.makeText(TextEditor2.this, "Upload Success", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Log.d(TAG, "onError: error " + error.getCode() + " " + error.getDescription());
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        Log.d(TAG, "onReschedule: error " + error.toString());
                    }
                }).dispatch();
        } else {
            Toast.makeText(TextEditor2.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
             selectedImageUri = data.getData();

            // Get the actual path of the image file
            String imagePath = getImagePath(selectedImageUri);

            // Insert the image into RichEditor
            if (imagePath != null) {
                initCo();
                mEditor.insertImage(Uri.fromFile(new File(imagePath)).toString(), "Image Description" +imagePath, 320);
                Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();


            }
        }
    }
    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            String imagePath = cursor.getString(column_index);
            cursor.close();
            return imagePath;
        }
        return uri.getPath(); // Fallback to URI.getPath() if cursor is null
    }
}

