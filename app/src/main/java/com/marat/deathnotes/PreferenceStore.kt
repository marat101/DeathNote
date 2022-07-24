package com.marat.deathnotes

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class PreferenceStore(context: Context) {

    companion object {
        const val SORT_ID = "sort"
    }

    private var preferences: SharedPreferences =
        context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)

    fun saveSort(sort:Sorting){
        preferences.edit().putInt(SORT_ID,sort.numSort).apply()
    }
    fun getSort():Int{
        return preferences.getInt(SORT_ID, Sorting.SORTINGBYDATE2.numSort)
    }
}