package com.example.paper_slide.officebuddy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.paper_slide.R;
import com.example.paper_slide.officebuddy.ppt.pptActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CardView pdf,doc,excel,ppt;
    private static final int videoPermissionId = 1;



    private ArrayList<String> vPermissionNameList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pdf = findViewById(R.id.pdfView);
        doc = findViewById(R.id.docView);
        excel = findViewById(R.id.excelView);
        ppt= findViewById(R.id.pptView);
        /*pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, pdfActivity.class));
            }
        });
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this , docActivity.class));
            }
        });
        excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, excelActivity.class));
            }
        });*/


        ppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMultiplePermission();
                startActivity(new Intent(MainActivity.this, pptActivity.class));
            }
        });


    }

 /*   public boolean checkMultiplePermission() {
        List<String> listPermissionNeeded = new ArrayList<>();
        for (String permission : videoPermissionNameList) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(permission);
            }
        }
        if (!listPermissionNeeded.isEmpty()) {
            requestPermissions(new String[]{}, videoPermissionId);
            return false;
        }
        return true;
    }*/

    private void initializePermissionList(){
        if(Build.VERSION.SDK_INT >= 33){
            vPermissionNameList = new ArrayList<>();
            vPermissionNameList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }else {
            vPermissionNameList = new ArrayList<>();
            vPermissionNameList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private boolean checkMultiplePermission(){
        initializePermissionList();

        ArrayList<String> listPermissionNeeded = new ArrayList<>();

        for(String permission : vPermissionNameList){
            if(ContextCompat.checkSelfPermission(this,
                    permission
            ) != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(permission);
            }
        }

        if(!listPermissionNeeded.isEmpty()){
            ActivityCompat.requestPermissions(
                    this,
                    listPermissionNeeded.toArray(new String[0]),
                    videoPermissionId
            );
            return false;
        }
        return true;
    }
}
