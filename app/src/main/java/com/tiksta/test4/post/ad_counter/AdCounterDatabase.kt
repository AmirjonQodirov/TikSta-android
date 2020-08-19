package com.tiksta.test4.post.ad_counter

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import com.tiksta.test4.R

const val COUNTER_DATABASE_NAME = "CounterDB"
const val COUNTER_TABLE_NAME = "Counter"
const val COUNTER_COL_COUNTER = "counter"
const val COUNTER_COL_ID = "id"

class AdCounterDatabase(private var context: Context) :
    SQLiteOpenHelper(context, COUNTER_DATABASE_NAME, null, 1) {
    private val fContext: Context? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + COUNTER_TABLE_NAME + " (" +
                COUNTER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COUNTER_COL_COUNTER + " INTEGER)"
        db?.execSQL(createTable)

        val insert = "INSERT INTO $COUNTER_TABLE_NAME($COUNTER_COL_COUNTER) VALUES(0);"

        db?.execSQL(insert)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun increaseCounter(): Boolean {
        val query = "SELECT $COUNTER_COL_COUNTER FROM $COUNTER_TABLE_NAME"

        val db = this.readableDatabase
        val result = db.rawQuery(query, null)
        var returnCounter = false

        var counter = 0
        var oldCounter = 0
        if (result.moveToNext()) {
            counter = result.getString(result.getColumnIndex(COUNTER_COL_COUNTER)).toInt()
            oldCounter = counter

            val limit = context.resources.getString(R.string.max_ad_counter).toInt()

            if (counter == limit) {
                counter = 0
                returnCounter = true
            }
        }
        result.close()

        val updateQuery =
            "UPDATE " + COUNTER_TABLE_NAME + " SET " + COUNTER_COL_COUNTER + " = " + (counter + 1) + " WHERE " + COUNTER_COL_COUNTER + " = " + oldCounter
        db?.execSQL(updateQuery)
        db?.close()

        return returnCounter
    }
}
