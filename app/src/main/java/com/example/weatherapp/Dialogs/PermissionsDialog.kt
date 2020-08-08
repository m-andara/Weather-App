package com.example.weatherapp.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding

class PermissionsDialog(private val onClick: (Int) -> Unit): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val binding = ActivityMainBinding.inflate(inflater)
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.location_permission_desc)
            .setView(binding.root)
            .setPositiveButton(R.string.approve) { _, _ ->
                    onClick(1)
                }
            .setNegativeButton(
                R.string.cancel) { _, _ ->
                onClick(0)
            }
        // Create the AlertDialog object and return it
        return builder.create()
    }
}
