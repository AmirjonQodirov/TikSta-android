package com.tiksta.test4.post.data

import android.app.Activity
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tiksta.test4.R
import com.tiksta.test4.post.PostFit
import com.tiksta.test4.post.Utils
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION", "UNREACHABLE_CODE")
class MyAsyncTask(
    private var progressDialog: ProgressDialog,
    private var post_length: SwitchCompat,
    private var editTextPost: EditText,
    private var editTextTag: EditText,
    private var resultTagsList: ArrayList<String>,
    private var view: View,
    private var isResultCalculated: Boolean,
    private var activity: FragmentActivity,
    private var post_display: SwitchCompat,
    private var copyToClipboard: Button,
    private var resultWithHashTags: TextView,
    private var div2: ConstraintLayout,
    private var chipGroup: ChipGroup
) : AsyncTask<Void, Void, String>() {

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        println("11111111111111111111111111")
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun doInBackground(vararg p0: Void?): String {
//        Thread.sleep(1000)
//        p0[0]?.open()
//        println("00000000000000000002220000")
//        return p0[0]?.readData(tag, platform)!!
        if ((post_length.isChecked && Utils.getPost() == null) || Utils.getTag() == null || ((Utils.getPost() != null || editTextPost.text.isNotEmpty()) && Utils.getPost() != editTextPost.text.toString()) || Utils.getTag() != editTextTag.text.toString() || Utils.isChipClosed()) {
            resultTagsList.clear()
            updateResultWithHashTags(
                resultTagsList,
                getActualPost(editTextPost.text.toString())
            )

            generateResultTags(view)

            println("Result: " + resultWithHashTags.text.toString())
            isResultCalculated = true

            if (editTextPost.text.isEmpty()) {
                Utils.setPost(null)
            } else {
                Utils.setPost(editTextPost.text.toString())
            }
            Utils.setTag(editTextTag.text.toString())
            Utils.setChipClosed(false)
        }
        activity?.let { it1 -> hideKeyboard(it1) }

        return "end"
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        progressDialog.dismiss()
        println("33333333333333333333333")
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun generateResultTags(view: View) {
        val chipGroup: ChipGroup = view.findViewById(R.id.chipGroup)
        val inflater: LayoutInflater = LayoutInflater.from(view.context)

        val tabPosition = Utils.getTabPosition()
        var platform = "instagram_id"

        if (tabPosition == 0) {
            platform = "tiktok_id"
        }

        val tagLength = editTextTag.length()
        if (tagLength == 0) {
            editTextTag.error = "This field is required!"
            return
        }

        val postLength = editTextPost.length()
        if (postLength < 0) {
            editTextPost.error = "Incorrect length!"
            return
        } else if (post_length.isChecked && postLength == 0) {
            editTextPost.error = "This field is required!"
            return
        } else if (platform == "instagram_id" && post_length.isChecked && postLength >= 2199) {
            editTextPost.error = "Your post is too long!"
            return
        } else if (platform == "tiktok_id" && post_length.isChecked && postLength >= 99) {
            editTextPost.error = "Your post is too long!"
            return
        }


//        val db = context?.let { it1 -> DataBaseHandler(it1) }
        val db = DatabaseAccess.getInstance(view.context)
        db.open()

        val tags = editTextTag.text.toString() + " "
        var currentTag = StringBuilder()
        val allTags: ArrayList<String> = ArrayList()

        for (c in tags) {
            if (Character.isWhitespace(c) || c == '#') {
                if (currentTag.toString().isNotEmpty()) {
                    allTags.add(currentTag.toString())
                    currentTag = StringBuilder()
                }
            } else {
                currentTag.append(Character.toLowerCase(c))
            }
        }

        val list: ArrayList<String> = ArrayList()
        val hasTag: TreeMap<String, Boolean> = TreeMap()

        print("Has: ")

        //todo


        for (tag in allTags) {

            val tmpTags = db.readData(tag, platform)
            for (tmpTag in tmpTags) {
                if (!hasTag.containsKey(tmpTag)) {
                    list.add(tmpTag)
                    print("$tmpTag ")
                    hasTag[tmpTag] = true
                }
            }
        }

        db.close()

        var currentHashTag = ""
        var hashTagsCount = 0
        var hashTagStarted = false


        val currentPostState = editTextPost.text.toString()
        val currentPostStateFor = "$currentPostState "
        for (c in currentPostStateFor) {
            if (!hashTagStarted && c == '#') {
                hashTagStarted = true
                continue
            }

            if (hashTagStarted) {
                if (c == '#' || !(c.isLetter() || c.isDigit())) {
                    if (currentHashTag.isNotEmpty()) {
                        if (hasTag.containsKey(currentHashTag)) {
                            hasTag[currentHashTag] = false
                        }

                        println("TAG: " + currentHashTag)

                        currentHashTag = ""

                        hashTagsCount++
                    }

                    hashTagStarted = false

                    if (c == '#') {
                        hashTagStarted = true
                    }
                } else {
                    currentHashTag += c
                }
            }
        }

        println("AAAAAAAAAAAAAAAAAAAAA " + hashTagsCount + ", " + platform)
        if (platform == "instagram_id" && hashTagsCount > 30) {
            editTextPost.error = "Too much hashtags in your post! ($hashTagsCount > 30)"
            return
        } else if (platform == "instagram_id" && hashTagsCount == 30) {
            Toast.makeText(view.context, "You already have 30/30 hashtags!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val data: ArrayList<String> = ArrayList()
        print("Result: ")
        for (element in list) {
            if (hasTag.containsKey(element) && hasTag[element] == true) {
                data.add(element)
                print(element + " ")
            }
        }
        println()

        if (data.isEmpty()) {
            println("pusto")
// TODO
//            Toast.makeText(view.context, "No hashtags found!", Toast.LENGTH_SHORT).show()
            return
        }

        val postFit = if (post_length.isChecked) {
            PostFit(postLength, data)
        } else {
            PostFit(0, data)
        }

        // Using 'switch' because code will look be more elegant if we add new platforms
        when (platform) {
            "instagram_id" -> resultTagsList =
                postFit.instagramFit(hashTagsCount) as ArrayList<String>
            "tiktok_id" -> resultTagsList = postFit.tikTokFit() as ArrayList<String>
        }

        if (resultTagsList.isEmpty()) {
            Toast.makeText(
                view.context,
                "Not enough space to insert even a hashtag!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        for (tag in resultTagsList) {
            val chip: Chip = inflater.inflate(R.layout.chip_item, null, false) as Chip
            chip.text = tag
            chip.setOnCloseIconClickListener {
                chipGroup.removeView(chip)
                Utils.setChipClosed(true)

                if (resultTagsList.size == 1) {
                    Toast.makeText(
                        view.context,
                        "All hashtags have been removed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                resultTagsList.remove(tag)

                updateResultWithHashTags(resultTagsList, getActualPost(currentPostState))
            }

            chipGroup.addView(chip)
        }

        updateResultWithHashTags(resultTagsList, getActualPost(currentPostState))
    }

    private fun updateResultWithHashTags(resultTagsList: ArrayList<String>, post: String) {
        if ((resultTagsList.isEmpty() && (!post_length.isChecked || (post_length.isChecked && (editTextPost.text.isEmpty() || !post_display.isChecked))))) {
            copyToClipboard.visibility = View.GONE
            resultWithHashTags.visibility = View.GONE
            div2.visibility = View.GONE

            return
        }

        var result = post
        var isFirst = true
        for (tag in resultTagsList) {
            if (isFirst) {
                result += "#$tag"
                isFirst = false
            } else {
                result += " #$tag"
            }
        }

        chipGroup.visibility = View.VISIBLE
        div2.visibility = View.VISIBLE
        copyToClipboard.visibility = View.VISIBLE
        resultWithHashTags.visibility = View.VISIBLE
        resultWithHashTags.text = result

    }

    private fun getActualPost(post: String): String {
        return if (post_length.isChecked && post_display.isChecked && (post.isNotEmpty() && !post[post.length - 1].isWhitespace())) {
            "$post\n"
        } else if (post_length.isChecked && post_display.isChecked && (post.isEmpty() || post[post.length - 1].isWhitespace())) {
            post
        } else {
            ""
        }
    }

}
