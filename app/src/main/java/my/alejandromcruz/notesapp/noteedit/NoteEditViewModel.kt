package my.alejandromcruz.notesapp.noteedit

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import my.alejandromcruz.notesapp.database.NotesDatabaseDao
import my.alejandromcruz.notesapp.model.Note
import kotlinx.coroutines.launch

class NoteEditViewModel(
    private val database: NotesDatabaseDao,
    private val noteId: Long
) : ViewModel() {

    private val _navigateToNotes = MutableLiveData<Boolean>()
    val navigateToNotes: LiveData<Boolean> = _navigateToNotes

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note> = _note

    private val _deleteNoteVisible = MutableLiveData<Int>()
    val deleteNoteVisible: LiveData<Int> = _deleteNoteVisible


    init {
        if (noteId != -1L) {
            initNote()
            _deleteNoteVisible.value = View.VISIBLE
        } else _deleteNoteVisible.value = View.GONE
    }

    private fun initNote() = viewModelScope.launch { _note.value = get(noteId) }

    fun editNote(title: String, content: String) {
        viewModelScope.launch {
            if (noteId == -1L) {
                insert(Note(title = title, content = content))
            } else {
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