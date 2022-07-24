package com.marat.deathnotes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 2)
abstract class NoteRoomDatabase() : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        fun getDatabase(context: Context) =
            Room.databaseBuilder(context, NoteRoomDatabase::class.java, "note_room_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}