package com.tiksta.test4.post.data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseAccess(context: Context) {
    private var openHelper: SQLiteOpenHelper = DatabaseOpenHelper(context)
    private var db: SQLiteDatabase? = null

    companion object {
        private var instance: DatabaseAccess? = null

        fun getInstance(context: Context) : DatabaseAccess {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }

            return instance as DatabaseAccess
        }
    }

    var c: Cursor? = null

    fun open() {
        this.db = openHelper.writableDatabase
    }

    fun close() {
        this.db?.close()
    }

    fun readData(tag: String, platform: String): MutableList<String> {
        var list: MutableList<String> = ArrayList()

        val query =
            "SELECT name FROM Tags WHERE name LIKE ('%' || \'" + tag + "\' || '%') ORDER BY " + platform + " ASC"

        val result = db?.rawQuery(query, null)
        if (result != null) {
            var count = 0
            if (result.moveToFirst()) {
                do {
                    val currentTag: String = result.getString(result.getColumnIndex(COL_NAME))
                    list.add(currentTag)
                    ++count
                } while (result.moveToNext() && count < 30)
            }

            result.close()
        }

        return list
    }
}