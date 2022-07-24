package com.marat.deathnotes

enum class Sorting(val numSort:Int) {
    SORTINGBYDATE(1),
    SORTINGBYDATE2(3),
    SORTINGBYTITLE(2);

    fun sort(note: List<Note>): List<Note> {
        return when (this) {
            SORTINGBYDATE -> note.sortedBy(Note::date)
            SORTINGBYDATE2 -> note.sortedBy(Note::date2).reversed()
            SORTINGBYTITLE -> note.sortedBy(Note::noteTitle)
        }
    }
}