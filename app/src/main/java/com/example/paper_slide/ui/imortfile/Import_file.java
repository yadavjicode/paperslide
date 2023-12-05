package com.example.paper_slide.ui.imortfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;


import com.example.paper_slide.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class Import_file extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ImageModel>list;
    ImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_file);
        recyclerView = findViewById(R.id.recyclerView);

        list = new ArrayList<>();

        GridLayoutManager layoutManager = new
                GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ImageAdapter(list,this);
        recyclerView.setAdapter(adapter);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        ReadSdcard(Import_file.this);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }



    private void ReadSdcard(Context context){
        Uri collection;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        }else
        {
            collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        }
        String projection[] = new String[]{
                MediaStore.Images.Media._ID
        };

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try(Cursor cursor = Import_file.this.getContentResolver().query(
                    collection,
                    projection,
                    null,
                    null
            )){
                int idColumn = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                while (cursor.moveToNext()){
                    long id = cursor.getLong(idColumn);
                    Uri contentUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id);
                    list.add(new ImageModel(contentUri));
                }
                adapter.notifyDataSetChanged();
                cursor.close();

            }
        }


    }
}