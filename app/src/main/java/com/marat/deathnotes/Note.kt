package com.marat.deathnotes


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey (autoGenerate = true) val id: Int?=null,
    var noteTitle: String,
    var noteText: String,
    val date: String,
    var date2: String
) : Parcelable
