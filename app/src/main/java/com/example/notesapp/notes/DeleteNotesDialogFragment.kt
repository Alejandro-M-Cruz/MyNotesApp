package com.example.notesapp.notes

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.notesapp.R

class DeleteNotesDialogFragment(private val viewModel: NotesViewModel) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_confirm_delete)
                .setPositiveButton(R.string.yes) { _, _ -> viewModel.clearNotes() }
                .setNegativeButton(R.string.no) { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}