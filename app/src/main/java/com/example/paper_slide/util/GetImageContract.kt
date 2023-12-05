package com.example.paper_slide.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class GetImageContract : ActivityResultContract<Unit, Uri?>() {



    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == android.app.Activity.RESULT_OK) {
            intent?.data
        } else {
            null
        }
    }
}
