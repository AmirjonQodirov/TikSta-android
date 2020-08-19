package com.tiksta.test4.post.ad_counter

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

const val COUNTER_DATABASE_NAME = "CounterDB"
const val COUNTER_TABLE_NAME = "Counter"
const val COUNTER_COL_COUNTER = "counter"
const val COUNTER_COL_ID = "id"

class AdCounterDatabase(private var context: Context): SQLiteOpenHelper(context, COUNTER_DATABASE_NAME, null, 1) {
    private val fContext: Context? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + COUNTER_TABLE_NAME + " (" +
                COUNTER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COUNTER_COL_COUNTER + " INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}