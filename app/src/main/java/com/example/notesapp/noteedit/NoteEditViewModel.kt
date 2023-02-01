package com.example.notesapp.noteedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.database.NotesDatabaseDao
import com.example.notesapp.model.Note
import kotlinx.coroutines.launch

class NoteEditViewModel(
    private val database: NotesDatabaseDao,
    private val noteId: Long
) : ViewModel() {

    private val _navigateToNotes = MutableLiveData<Boolean>()
    val navigateToNotes: LiveData<Boolean> = _navigateToNotes

    private var _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    init { if (noteId != -1L) initNote() }

    private fun initNote() = viewModelScope.launch { _note.value = get(noteId) }

    fun editNote(title: String, content: String) {
        viewModelScope.launch {
            if (noteId == -1L) insert(Note(title = title, content = content))
            else {
                update(_note.value!!.apply {
                    this.title = title
                    this.content = content
                })
            }
            _navigateToNotes.value = true
        }
    }

    fun cancelChanges() { _navigateToNotes.value = true }

    fun deleteNote() {
        viewModelScope.launch {
            delete(_note.value!!)
            _navigateToNotes.value = true
        }
    }

    private suspend fun get(noteId: Long) = database.get(noteId)

    private suspend fun insert(note: Note) = database.insert(note)

    private suspend fun update(note: Note) = database.update(note)

    private suspend fun delete(note: Note) = database.delete(note)

    fun doneNavigating() { _navigateToNotes.value = false }
}