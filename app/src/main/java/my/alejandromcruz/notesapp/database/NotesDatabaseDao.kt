package my.alejandromcruz.notesapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import my.alejandromcruz.notesapp.model.Note

@Dao
interface NotesDatabaseDao {

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM notes_table WHERE noteId = :id")
    suspend fun get(id: Long): Note?

    @Query("DELETE FROM notes_table")
    suspend fun clear()

    @Query("SELECT * FROM notes_table ORDER BY noteId")
    fun getAllNotes(): LiveData<List<Note>>

}