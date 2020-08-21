package com.tiksta.ads.post.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tiksta.ads.post.Utils

class DatabaseAccess(context: Context) {
    private var openHelper: SQLiteOpenHelper = DatabaseOpenHelper(context)
    private var db: SQLiteDatabase? = null

    companion object {
        private var instance: DatabaseAccess? = null

        fun getInstance(context: Context): DatabaseAccess {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }

            return instance as DatabaseAccess
        }
    }

    fun open() {
        this.db = openHelper.writableDatabase
    }

    fun close() {
        this.db?.close()
    }

    fun readData(tag: String, platform: String): MutableList<String> {
        val list: MutableList<String> = ArrayList()

        val queryPrefix =
            "SELECT name FROM Tags WHERE name LIKE (\'$tag\' || '%') ORDER BY $platform ASC"

        val resultPrefix = db?.rawQuery(queryPrefix, null)
        if (resultPrefix != null) {
            if (resultPrefix.moveToFirst()) {
                do {
                    val currentTag: String =
                        resultPrefix.getString(resultPrefix.getColumnIndex(COL_NAME))
                    list.add(currentTag)
                    Utils.setHashTagsFound(Utils.getHashTagsFound() + 1)
                } while (resultPrefix.moveToNext() && Utils.getHashTagsFound() < 33)
            }

            resultPrefix.close()
        }

        val querySubstring =
            "SELECT name FROM Tags WHERE name LIKE ('%' || \'$tag\' || '%') ORDER BY $platform ASC"
        val resultSubstring = db?.rawQuery(querySubstring, null)
        if (resultSubstring != null) {
            if (resultSubstring.moveToFirst()) {
                do {
                    val currentTag: String =
                        resultSubstring.getString(resultSubstring.getColumnIndex(COL_NAME))
                    list.add(currentTag)
                    Utils.setHashTagsFound(Utils.getHashTagsFound() + 1)
                } while (resultSubstring.moveToNext() && Utils.getHashTagsFound() < 33)
            }

            resultSubstring.close()
        }

        return list.distinct().toMutableList()
    }
}
