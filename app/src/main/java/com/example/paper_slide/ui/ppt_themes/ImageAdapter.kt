package com.example.paper_slide.ui.ppt_themes

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.paper_slide.R


class ImageAdapter(private var imageUris: List<Uri>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.theme_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUri = imageUris[position]
        holder.bind(imageUri)
    }

    override fun getItemCount(): Int {
        return imageUris.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(imageUri: Uri) {
            imageView.setImageURI(imageUri)
        }
    }

    /*fun setData(newData: List<Uri>) {
        imageUris.clear()
        imageUris.addAll(newData)
        notifyDataSetChanged()
    }*/
}