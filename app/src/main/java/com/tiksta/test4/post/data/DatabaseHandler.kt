package com.tiksta.test4.post.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi


val DATABASE_NAME = "TikStaDB"
val TABLE_NAME = "Tags"
val COL_NAME = "name"
val COL_INSTAGRAM_ID = "instagram_id"
val COL_TIKTOK_ID = "tiktok_id"
val COL_ID = "id"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(db: SQLiteDatabase?) {
        println("Creating TABLE...")
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(256)," +
                COL_INSTAGRAM_ID + " INTEGER," +
                COL_TIKTOK_ID + " INTEGER)"

//        var directory = File(System.getProperty("user.dir"));
//
//        // Get all files from a directory.
//        var fList = directory.listFiles();
//        if(fList != null)
//            for (i in 0 until fList.size) {
//                var file = fList[i]
//                if (file.isFile()) {
//                    println(file.name)
//                } else if (file.isDirectory()) {
//                    println(file.name)
//                }
//            }
//        println("FFFFFFFFF: " + Paths.get(".").toAbsolutePath().normalize().toString())
//        println(System.getProperty("user.dir"))
        var inserts : String = "INSERT INTO Tags (name, instagram_id, tiktok_id) VALUES 	('love',1, 287)"
//        val insertData = "INSERT INTO Tags (name, instagram_id, tiktok_id) VALUES('love',1,287), ('instagood',2,288)," +
//                "('fashion',3,289), ('photooftheday',4,290), ('beautiful',5,291), ('art',6,292), ('photography',7,293), " +
//                "('happy',8,294), ('picoftheday',9,295), ('cute',10,158);"


        db?.execSQL(createTable)
        db?.execSQL(inserts)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun readData(tag: String, platform: String): MutableList<String> {
        var list: MutableList<String> = ArrayList()

        val db = this.readableDatabase
        val query =
            "SELECT name FROM Tags WHERE name LIKE ('%' || \'" + tag + "\' || '%') ORDER BY " + platform + " ASC"
//        "SELECT name FROM Tags"
        println(query)
        val result = db.rawQuery(query, null)
        var count = 0
        if (result.moveToFirst()) {
            do {
                var currentTag: String = ""
                currentTag = result.getString(result.getColumnIndex(COL_NAME))
                list.add(currentTag)
                ++count
            } while (result.moveToNext() && count < 30)
        }

        result.close()

//        db.delete(TABLE_NAME, null, null)
        db.close()
        return list
    }
}