package com.example.notesapp.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NotesDatabaseDao
import com.example.notesapp.model.Note
import kotlinx.coroutines.launch

class NotesViewModel(private val database: NotesDatabaseDao) : ViewModel() {
    val notes: LiveData<List<Note>> = database.getAllNotes()

    private val _navigateToNoteEdit = MutableLiveData<Long?>()
    val navigateToNoteEdit: LiveData<Long?> = _navigateToNoteEdit

    private val _showConfirmDeleteDialog = MutableLiveData<Boolean>()
    val showConfirmDeleteDialog: LiveData<Boolean> = _showConfirmDeleteDialog

    private val _showNotesClearedMessage = MutableLiveData<Boolean>()
    val showNotesClearedMessage: LiveData<Boolean> = _showNotesClearedMessage

    val clearButtonEnabled = Transformations.map(notes) { it.isNotEmpty() }

    fun addNote() {
        _navigateToNoteEdit.value = -1L
    }

    fun editNote(noteId: Long) {
        _navigateToNoteEdit.value = noteId
    }

    fun onClickDelete() {
        _showConfirmDeleteDialog.value = true
    }

    fun clearNotes() {
        viewModelScope.launch {
            clear()
            _showNotesClearedMessage.value = true
        }
    }

    private suspend fun clear() = database.clear()

    fun doneNavigating() {
        _navigateToNoteEdit.value = null
    }

    fun doneShowingNotesClearedMessage() {
        _showNotesClearedMessage.value = false
    }

    fun doneShowingDeleteConfirmation() {
        _showConfirmDeleteDialog.value = false
    }
}