package com.tiksta.test4.post.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

const val DATABASE_NAME = "TikStaDB"
const val TABLE_NAME = "Tags"
const val COL_NAME = "name"
const val COL_INSTAGRAM_ID = "instagram_id"
const val COL_TIKTOK_ID = "tiktok_id"
const val COL_ID = "id"

class DataBaseHandler(private var context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(db: SQLiteDatabase?) {
        println("Creating TABLE...")
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(256)," +
                COL_INSTAGRAM_ID + " INTEGER," +
                COL_TIKTOK_ID + " INTEGER)"

        val inserts: String = "INSERT INTO Tags (name, instagram_id, tiktok_id) VALUES" +
                "('love',1, 287)," +
                "('instagood',2, 288)," +
                "('fashion',3, 289)," +
                "('photooftheday',4, 290)," +
                "('beautiful',5, 291)," +
                "('art',6, 292)," +
                "('photography',7, 293)," +
                "('happy',8, 294)," +
                "('picoftheday',9, 295)," +
                "('cute',10, 158)," +
                "('follow',11, 296)," +
                "('tbt',12, 297)," +
                "('followme',13, 298)," +
                "('nature',14, 299)," +
                "('like4like',15, 300)," +
                "('travel',16, 301)," +
                "('instagram',17, 302)," +
                "('style',18, 303)," +
                "('repost',19, 304)," +
                "('summer',20, 305)," +
                "('instadaily',21, 306)," +
                "('selfie',22, 307)," +
                "('me',23, 308)," +
                "('friends',24, 309)," +
                "('fitness',25, 90)," +
                "('girl',26, 310)," +
                "('food',27, 311)," +
                "('fun',28, 312)," +
                "('beauty',29, 313)," +
                "('instalike',30, 314)," +
                "('smile',31, 315)," +
                "('family',32, 316)," +
                "('photo',33, 317)," +
                "('life',34, 53)," +
                "('likeforlike',35, 318)," +
                "('music',36, 29)," +
                "('ootd',37, 319)," +
                "('follow4follow',38, 320)," +
                "('makeup',39, 321)," +
                "('amazing',40, 322)," +
                "('igers',41, 323)," +
                "('nofilter',42, 324)," +
                "('dog',43, 325)," +
                "('model',44, 326)," +
                "('sunset',45, 327)," +
                "('beach',46, 328)," +
                "('instamood',47, 329)," +
                "('foodporn',48, 330)," +
                "('motivation',49, 331)," +
                "('followforfollow',50, 332)," +
                "('design',51, 333)," +
                "('lifestyle',52, 334)," +
                "('sky',53, 335)," +
                "('l4l',54, 336)," +
                "('f4f',55, 337)," +
                "('일상',56, 338)," +
                "('cat',57, 339)," +
                "('handmade',58, 340)," +
                "('hair',59, 341)," +
                "('vscocam',60, 342)," +
                "('bestoftheday',61, 343)," +
                "('vsco',62, 344)," +
                "('funny',63, 10)," +
                "('dogsofinstagram',64, 345)," +
                "('drawing',65, 346)," +
                "('artist',66, 233)," +
                "('gym',67, 92)," +
                "('flowers',68, 347)," +
                "('baby',69, 348)," +
                "('wedding',70, 349)," +
                "('girls',71, 350)," +
                "('instapic',72, 351)," +
                "('pretty',73, 352)," +
                "('likeforlikes',74, 353)," +
                "('photographer',75, 354)," +
                "('instafood',76, 355)," +
                "('party',77, 356)," +
                "('inspiration',78, 357)," +
                "('lol',79, 358)," +
                "('cool',80, 359)," +
                "('workout',81, 107)," +
                "('likeforfollow',82, 360)," +
                "('swag',83, 361)," +
                "('fit',84, 362)," +
                "('healthy',85, 97)," +
                "('yummy',86, 363)," +
                "('blackandwhite',87, 364)," +
                "('foodie',88, 365)," +
                "('moda',89, 366)," +
                "('home',90, 367)," +
                "('christmas',91, 368)," +
                "('black',92, 369)," +
                "('memes',93, 370)," +
                "('holiday',94, 371)," +
                "('pink',95, 372)," +
                "('sea',96, 373)," +
                "('landscape',97, 374)," +
                "('blue',98, 375)," +
                "('london',99, 376)," +
                "('winter',100, 377)"

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
        println(query)
        val result = db.rawQuery(query, null)
        var count = 0
        if (result.moveToFirst()) {
            do {
                val currentTag: String = result.getString(result.getColumnIndex(COL_NAME))
                list.add(currentTag)
                ++count
            } while (result.moveToNext() && count < 30)
        }

        result.close()

        db.close()
        return list
    }
}
