package com.example.paper_slide.ui.pptthemes

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paper_slide.R
import com.example.paper_slide.databinding.ActivityPptthemesBinding

class PPTThemesActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPptthemesBinding
    private val context = this@PPTThemesActivity
    private val selectedImageUris = mutableListOf<Uri>()
    private lateinit var adapter: ImageAdapter

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
       uri?.let {
           selectedImageUris.add(it)
           // Save the updated list to SharedPreferences
      /*     saveImageUris()
           // Update the RecyclerView with the new list of image URIs
           adapter.setData(selectedImageUris)*/
           adapter.notifyDataSetChanged()
       }
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context,R.layout.activity_pptthemes)
        initSpinner()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = ImageAdapter(selectedImageUris)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3, RecyclerView.HORIZONTAL, false)
/*
        // Load saved image URIs from SharedPreferences
        loadSavedImageUris()

        // Set the data in the adapter
        adapter.setData(selectedImageUris)*/

        binding.uploadImg.setOnClickListener {
            getContent.launch("image/*")
        }
    }





    private fun initSpinner() {
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.number_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.slideSpinner.adapter = adapter
        }

        // Set a default selection message
        binding.slideSpinner.prompt = "Select Slide"

        // Set an item selected listener for the spinner
        binding.slideSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item as a toast
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, selectedItem, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

   /* private fun saveImageUris() {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val uriSet = selectedImageUris.map { it.toString() }.toSet()
        editor.putStringSet(KEY_IMAGE_URIS, uriSet)
        editor.apply()
    }

    private fun loadSavedImageUris() {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val uriSet = sharedPreferences.getStringSet(KEY_IMAGE_URIS, null)
        uriSet?.let {
            selectedImageUris.addAll(it.map { Uri.parse(it) })
        }
    }

    companion object {
        private const val KEY_IMAGE_URIS = "key_image_uris"
    }*/

}
