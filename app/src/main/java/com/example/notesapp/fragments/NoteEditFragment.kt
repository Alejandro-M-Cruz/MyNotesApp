package com.example.notesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.database.NotesDatabase
import com.example.notesapp.databinding.FragmentNoteEditBinding

class NoteEditFragment : Fragment() {
    private lateinit var noteEditViewModel: NoteEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNoteEditBinding>(
            inflater, R.layout.fragment_note_edit, container, false
        )
        val context = requireActivity().applicationContext
        noteEditViewModel = NoteEditViewModel(
            NotesDatabase.getInstance(context).notesDatabaseDao,
            NoteEditFragmentArgs.fromBundle(requireArguments()).noteId
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.noteEditViewModel = noteEditViewModel

        binding.confirmButton.setOnClickListener {
            noteEditViewModel.editNote(
                binding.titleEditText.text.toString(),
                binding.contentEditText.text.toString()
            )
        }

        noteEditViewModel.navigateToNotes.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    NoteEditFragmentDirections.actionNoteEditFragmentToNotesFragment()
                )
                noteEditViewModel.doneNavigating()
            }
        }
        return binding.root
    }

}