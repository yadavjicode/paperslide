package com.example.paper_slide.ui.imortfile;

import android.net.Uri;

public class ImageModel {


    private Uri path;

    public ImageModel(Uri path) {
        this.path = path;
    }

    public Uri getPath() {
        return path;
    }

    public void setPath(Uri path) {
        this.path = path;
    }
}
