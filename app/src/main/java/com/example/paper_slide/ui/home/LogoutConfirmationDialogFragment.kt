package com.example.paper_slide.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LogoutConfirmationDialogFragment : DialogFragment() {

    interface LogoutConfirmationListener {
        fun onLogoutConfirmed()
        fun onLogoutCancelled()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Logout") { _, _ ->
                    (activity as? LogoutConfirmationListener)?.onLogoutConfirmed()
                }
                .setNegativeButton("Cancel") { _, _ ->
                    (activity as? LogoutConfirmationListener)?.onLogoutCancelled()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
