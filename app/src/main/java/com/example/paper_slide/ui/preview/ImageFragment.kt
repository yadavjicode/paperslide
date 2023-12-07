package com.example.paper_slide.ui.preview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.paper_slide.R

class ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView : ImageView = view.findViewById(R.id.scannedImage)

       // val scannedImage = arguments?.getParcelable("imageUri")
        val receivedUri: Uri? = arguments?.getParcelable("imageUri")

        // Load the Bitmap from the Uri
        val bitmap: Bitmap? = receivedUri?.let { loadBitmapFromUri(it) }

        // Set the Bitmap to the ImageView
        bitmap?.let {
            imageView.setImageBitmap(it)
        }    
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            // Use content resolver to open the input stream from the Uri
            val inputStream = requireContext().contentResolver.openInputStream(uri)

            // Decode the stream into a Bitmap
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}