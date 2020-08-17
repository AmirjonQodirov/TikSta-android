package com.tiksta.test4.post.data

import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.fragment.app.FragmentActivity
import com.tiksta.test4.R
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class MyAsyncTaskMain(
    private var progressDialog: ProgressDialog,
    private var platform: String,
    private var allTags: ArrayList<String>,
    private var db: DatabaseAccess,
    private var activity: FragmentActivity,
    private var hasTag: TreeMap<String, Boolean>
) :
    AsyncTask<Void, Void, ArrayList<String>>() {

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        println("11111111111111111111111111")
    }

    override fun doInBackground(vararg void: Void): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()
        for (tag in allTags) {

            val tmpTags = db.readData(tag, platform)
//            progressDialog = ProgressDialog(activity)
//            val tmpTags =  MyAsyncTask(progressDialog, tag,platform).execute(db).get()
            for (tmpTag in tmpTags) {
                if (!hasTag.containsKey(tmpTag)) {
                    list.add(tmpTag)
                    print("$tmpTag ")
                    hasTag[tmpTag] = true
                }
            }
        }

        return list
    }

    override fun onPostExecute(list: ArrayList<String>?) {
        super.onPostExecute(list)
        progressDialog.dismiss()
        println("33333333333333333333333")

    }


}