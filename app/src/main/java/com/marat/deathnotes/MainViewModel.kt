package com.marat.deathnotes

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class MainViewModel(
    var preferenceStore: PreferenceStore,
    var db: NoteRoomDatabase
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val date2 = LocalDateTime.now().toString()

    @RequiresApi(Build.VERSION_CODES.O)
    val date = LocalDate.now().toString()

    var selectedSort = preferenceStore.getSort()
    var allNotes: LiveData<List<Note>> = db.noteDao().getSortedByDate2()

    @RequiresApi(Build.VERSION_CODES.O)
    fun newNote(title: String, text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (title != "" || text != "") {
                db.noteDao().insert(
                    Note(
                        noteTitle = title,
                        noteText = text,
                        date = date,
                        date2 = date2,
                        favourite = false
                    )
                )
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            db.noteDao().delete(note)
        }
    }

    fun addDelFav(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            note.favourite = note.favourite != true
            db.noteDao().update(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            db.noteDao().update(note)
        }
    }

    fun selectedSort(note: List<Note>, sorting: Sorting): List<Note> {
        return sorting.sort(note)
    }

    fun sortingName(): String {
        return when (preferenceStore.getSort()) {
            1 -> "По дате создания"
            2 -> "По заголовку"
            3 -> "По дате редактирования"
            else -> {
                "Сортировка"
            }
        }
    }

    fun sorting(num: Int): Sorting {
        return when (num) {
            Sorting.SORTINGBYDATE.numSort -> {
                Sorting.SORTINGBYDATE
            }
            Sorting.SORTINGBYTITLE.numSort -> {
                Sorting.SORTINGBYTITLE
            }
            Sorting.SORTINGBYDATE2.numSort -> {
                Sorting.SORTINGBYDATE2
            }
            else -> {
                Sorting.SORTINGBYDATE2
            }
        }
    }

    class MainViewModelFactory(context: Context) :
        ViewModelProvider.Factory {

        private var preferenceStore = PreferenceStore(context = context)
        private var db = NoteRoomDatabase.getDatabase(context)

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(
                preferenceStore,
                db
            ) as T
        }
    }
}