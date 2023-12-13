package com.example.paper_slide.ui.mindmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.example.paper_slide.R

class MindmapActivity : AppCompatActivity() {
    lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mindmap)


       val  webView : WebView= findViewById(R.id.webview)
         progressBar = findViewById(R.id.progress_bar)

        // Enable JavaScript (if your mind map requires it)
        webView.settings.javaScriptEnabled = true

        // Set a WebViewClient to handle redirects within the WebView
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                // Page has finished loading, hide the progress bar, and show the WebView
                progressBar.visibility = android.view.View.GONE
                webView.visibility = android.view.View.VISIBLE
            }
        }

        // Load the mind map link
        val mindMapUrl = "http://3.252.227.75:8005/mindmap/alice-4"
        webView.loadUrl(mindMapUrl)
    }




    override fun onBackPressed() {
        val webView: WebView = findViewById(R.id.webview)
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }

    }
}