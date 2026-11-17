package com.hvacpro.faultcodes.tuan9.bai1

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.hvacpro.faultcodes.tuan9.R

class MenuDialogFragment : DialogFragment() {
    private lateinit var listener: Filter
    var selectedOption = 0
    @Override
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Filter
    }
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
                    options, selectedOption
                ) { _, which -> selectedOption = which}
                .setPositiveButton("OK") { _, _ -> listener.ok(selectedOption)}
                .setNegativeButton("Cancel") { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

