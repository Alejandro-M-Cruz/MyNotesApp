package my.alejandromcruz.notesapp.notes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import my.alejandromcruz.notesapp.R
import my.alejandromcruz.notesapp.adapters.ItemAdapter
import my.alejandromcruz.notesapp.database.NotesDatabase
import my.alejandromcruz.notesapp.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var appContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_notes, container,false
        )
        appContext = requireActivity().applicationContext
        notesViewModel = NotesViewModel(
            NotesDatabase.getInstance(appContext).notesDatabaseDao
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.notesViewModel = notesViewModel
        (binding.notesList.layoutManager as GridLayoutManager).spanCount = 2

        initObservers()

        return binding.root
    }

    private fun initObservers() {
        notesViewModel.notes.observe(viewLifecycleOwner) {
            binding.notesList.adapter = ItemAdapter(
                appContext,
                notesViewModel.notes.value!!,
                notesViewModel
            )
        }

        notesViewModel.navigateToNoteEdit.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(
                    NotesFragmentDirections.actionNotesFragmentToNoteEditFragment(it)
                )
                notesViewModel.doneNavigating()
            }
        }

        notesViewModel.showConfirmDeleteDialog.observe(viewLifecycleOwner) {
            if (it) {
                DeleteNotesDialogFragment(notesViewModel)
                    .show(parentFragmentManager, "confirm_delete")
                notesViewModel.doneShowingDeleteConfirmation()
            }
        }

        notesViewModel.showNotesClearedMessage.observe(viewLifecycleOwner) {
            if(it) {
                Toast.makeText(
                    appContext,
                    resources.getString(R.string.notes_cleared_message),
                    Toast.LENGTH_LONG
                ).show()
                notesViewModel.doneShowingNotesClearedMessage()
            }
        }
    }
}