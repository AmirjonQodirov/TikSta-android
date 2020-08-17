package com.tiksta.test4.post.data

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask

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

        val query1 =
            "SELECT name FROM Tags WHERE name LIKE (\'$tag\' || '%') ORDER BY $platform ASC"

        var count = 0
        val result1 = db?.rawQuery(query1, null)
        if (result1 != null) {
            if (result1.moveToFirst()) {
                do {
                    val currentTag: String = result1.getString(result1.getColumnIndex(COL_NAME))
                    list.add(currentTag)
                    ++count
                } while (result1.moveToNext() && count < 33)
            }

            result1.close()
        }

        val query2 =
            "SELECT name FROM Tags WHERE name LIKE ('%' || \'$tag\') ORDER BY $platform ASC"
        val result2 = db?.rawQuery(query2, null)
        if (result2 != null) {
            if (result2.moveToFirst()) {
                do {
                    val currentTag: String = result2.getString(result2.getColumnIndex(COL_NAME))
                    list.add(currentTag)
                    ++count
                } while (result2.moveToNext() && count < 33)
            }

            result2.close()
        }

        val query3 =
            "SELECT name FROM Tags WHERE name LIKE ('_%' || \'$tag\' || '_%') ORDER BY $platform ASC"
        val result3 = db?.rawQuery(query3, null)
        if (result3 != null) {
            if (result3.moveToFirst()) {
                do {
                    val currentTag: String = result3.getString(result3.getColumnIndex(COL_NAME))
                    list.add(currentTag)
                    ++count
                } while (result3.moveToNext() && count < 33)
            }

            result3.close()
        }

        return list.distinct().toMutableList()
    }
}