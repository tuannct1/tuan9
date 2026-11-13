package com.hvacpro.faultcodes.tuan9.bai1

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.hvacpro.faultcodes.tuan9.R

class MenuDialogFragment : DialogFragment() {
    val options = arrayOf("Modification time (Newest first)",
        "Modification time (Oldest first)",
        "Creation time(Newest first)",
        "Creation Time(Oldest first)",
        "Name A-Z", "Name Z-A")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.CustomRadioDialogTheme)
            builder
                .setTitle("Sort by")
                .setSingleChoiceItems(
                    options, 0
                ) { dialog, which -> }
                .setPositiveButton("OK") { dialog, which -> }
                .setNegativeButton("Cancel") { dialog, which -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

