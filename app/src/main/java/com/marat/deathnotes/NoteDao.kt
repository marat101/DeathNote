package com.marat.deathnotes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT*FROM Note ORDER BY id DESC")
    fun getSortedByDate2(): LiveData<List<Note>>

    @Insert(entity = Note::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete(entity = Note::class)
    fun delete(note: Note)

    @Query("SELECT * FROM Note WHERE id = :noteId")
    fun getById(noteId: Int): Note

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(note: Note)
}