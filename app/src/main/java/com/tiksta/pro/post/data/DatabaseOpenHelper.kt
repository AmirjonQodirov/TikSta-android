package com.tiksta.pro.post.data

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

const val TAGS_DATABASE_NAME = "TikStaDB.db"
const val TAGS_DATABASE_VERSION = 1
const val COL_NAME = "name"

class DatabaseOpenHelper(context: Context) :
    SQLiteAssetHelper(context, TAGS_DATABASE_NAME, null, TAGS_DATABASE_VERSION) {
}
