package my.alejandromcruz.notesapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0L,

    @ColumnInfo("title")
    var title: String = "",

    @ColumnInfo("content")
    var content: String = ""

)