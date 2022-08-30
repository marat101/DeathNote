package com.marat.deathnotes

enum class Sorting(val numSort:Int) {
    SORTINGBYDATE(1),
    SORTINGBYDATE2(3),
    SORTINGBYTITLE(2);

    fun sort(note: List<Note>): List<Note> {
        return when (this) {
            SORTINGBYDATE -> note.sortedBy(Note::date).reversed().sortedBy(Note::favourite).reversed()
            SORTINGBYDATE2 -> note.sortedBy(Note::date2).sortedBy(Note::favourite).reversed()
            SORTINGBYTITLE -> note.sortedBy(Note::noteTitle).reversed().sortedBy(Note::favourite).reversed()
        }
    }
}