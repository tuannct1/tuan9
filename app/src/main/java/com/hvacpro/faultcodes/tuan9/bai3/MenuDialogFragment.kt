package com.hvacpro.faultcodes.tuan9.bai3

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.bai4.Filter

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

