package com.tiksta.pro.post

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tiksta.pro.R
import com.tiksta.pro.post.data.DatabaseAccess
import kotlinx.android.synthetic.main.activity_post_filling.*
import java.lang.Character.isWhitespace
import java.lang.Character.toLowerCase
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.N)
class BlankFragment : Fragment() {
    private lateinit var viewModel: BlankViewModel
    private var resultTagsList: ArrayList<String> = ArrayList()
    private var isResultCalculated = false
    private lateinit var progressDialog: ProgressDialog
    private var platform = ""
    private var hasTag: TreeMap<String, Boolean> = TreeMap()
    private var allTags: ArrayList<String> = ArrayList()
    private var hashTagsCount = 0

    private fun addChipsToGroup() {
        chipGroup.removeAllViews()
        for (chip in Utils.getChipsFromGroup()) {
            chipGroup.addView(chip)
        }
        Utils.setChipsFromGroup(ArrayList())
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus

        if (view == null) {
            view = View(activity)
        }

        imm.hideSoftInputFromWindow(view.windowToken, 0)
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

    private fun updateResultWithHashTagsForNewThread(
        resultTagsList: ArrayList<String>,
        post: String
    ) {
        if ((resultTagsList.isEmpty() && (!post_length.isChecked || (post_length.isChecked && (editTextPost.text.isEmpty() || !post_display.isChecked))))) {
            Utils.setCopyToClipboardVisible(false)
            Utils.setResultWithHashtagsVisible(false)
            Utils.setDiv2Visible(false)
            Utils.setChipGroupVisible(false)
            Utils.setResultWithHashtagsText("")

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

        Utils.setChipGroupVisible(true)
        Utils.setCopyToClipboardVisible(true)
        Utils.setResultWithHashtagsVisible(true)
        Utils.setDiv2Visible(true)
        Utils.setResultWithHashtagsText(result)
    }

    private fun updateResultWithHashTags(resultTagsList: ArrayList<String>, post: String) {
        if ((resultTagsList.isEmpty() && (!post_length.isChecked || (post_length.isChecked && (editTextPost.text.isEmpty() || !post_display.isChecked))))) {
            copyToClipboard.visibility = View.GONE
            resultWithHashTags.visibility = View.GONE
            div2.visibility = View.GONE
            chipGroup.visibility = View.GONE
            resultWithHashTags.text = ""

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

    private fun initialCheck(): Boolean {
        val tabPosition = Utils.getTabPosition()

        if (tabPosition == 0) {
            platform = getString(R.string.tiktok_id)
        }

        val tagLength = editTextTag.length()
        if (tagLength == 0) {
            editTextTag.error = getString(R.string.field_required)
            return false
        }

        val postLength = editTextPost.length()
        if (postLength < 0) {
            editTextPost.error = getString(R.string.incorrect_length)
            return false
        } else if (post_length.isChecked && postLength == 0) {
            editTextPost.error = getString(R.string.field_required)
            return false
        } else if (platform == getString(R.string.instagram_id) && post_length.isChecked && postLength >= 2199) {
            editTextPost.error = getString(R.string.post_too_long)
            return false
        } else if (platform == getString(R.string.tiktok_id) && post_length.isChecked && postLength >= 99) {
            editTextPost.error = getString(R.string.post_too_long)
            return false
        }

        val tags = editTextTag.text.toString() + " "
        var currentTag = StringBuilder()

        allTags = ArrayList()
        for (c in tags) {
            if (isWhitespace(c) || c == '#') {
                if (currentTag.toString().isNotEmpty()) {
                    allTags.add(currentTag.toString())
                    currentTag = StringBuilder()
                }
            } else {
                currentTag.append(toLowerCase(c))
            }
        }

        var currentHashTag = ""
        var hashTagStarted = false

        hasTag = TreeMap()
        hashTagsCount = 0

        val currentPostStateFor = "${editTextPost.text} "
        for (c in currentPostStateFor) {
            if (!hashTagStarted && c == '#') {
                hashTagStarted = true
                continue
            }

            if (hashTagStarted) {
                if (c == '#' || !(c.isLetter() || c.isDigit())) {
                    if (currentHashTag.isNotEmpty()) {
                        hasTag[currentHashTag] = false
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

        if (platform == getString(R.string.instagram_id) && hashTagsCount > 30) {
            editTextPost.error = getString(R.string.too_much_hashtags) + "($hashTagsCount > 30)"
            return false
        } else if (platform == getString(R.string.instagram_id) && hashTagsCount == 30) {
            Toast.makeText(view?.context, getString(R.string.have_30_of_30), Toast.LENGTH_SHORT)
                .show()
            return false
        }

        return true
    }

    private fun generateResultTags(view: View) {
        val chipGroup: ChipGroup = view.findViewById(R.id.chipGroup)
        val inflater: LayoutInflater = LayoutInflater.from(view.context)
        val list: ArrayList<String> = ArrayList()
        val db = DatabaseAccess.getInstance(view.context)

        db.open()
        Utils.setHashTagsFound(0)

        if (allTags.size > 30) {
            allTags = ArrayList(allTags.subList(0, 31))
        }

        for (tag in allTags) {
            val tmpTags = db.readData(tag, platform)
            for (tmpTag in tmpTags) {
                if (!hasTag.containsKey(tmpTag)) {
                    list.add(tmpTag)
                    hasTag[tmpTag] = true

                    if (list.size == 33) {
                        break
                    }
                }
            }

            if (list.size == 33) {
                break
            }
        }

        db.close()

        val data: ArrayList<String> = ArrayList()
        for (element in list) {
            if (hasTag.containsKey(element) && hasTag[element] == true) {
                data.add(element)
            }
        }

        if (data.isEmpty()) {
            Utils.setErrorMessage(getString(R.string.no_hashtags_found))
            return
        }

        val postLength = editTextPost.length()
        val postFit = if (post_length.isChecked) {
            PostFit(postLength, data)
        } else {
            PostFit(0, data)
        }

        // Using 'switch' because code will be more elegant if we add new platforms
        when (platform) {
            getString(R.string.instagram_id) -> resultTagsList =
                postFit.instagramFit(hashTagsCount)
            getString(R.string.tiktok_id) -> resultTagsList = postFit.tikTokFit()
        }

        if (resultTagsList.isEmpty()) {
            Utils.setErrorMessage(getString(R.string.not_enough_space))
            return
        }

        val currentPostState = editTextPost.text.toString()
        for (tag in resultTagsList) {
            val chip: Chip = inflater.inflate(R.layout.chip_item, null, false) as Chip
            chip.text = tag
            chip.setOnCloseIconClickListener {
                chipGroup.removeView(chip)
                Utils.setChipClosed(true)
                resultTagsList.remove(tag)
                updateResultWithHashTags(resultTagsList, getActualPost(currentPostState))

                if (resultTagsList.isEmpty()) {
                    Toast.makeText(
                        view.context,
                        getString(R.string.all_hashtags_removed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            Utils.addChip(chip)
        }

        updateResultWithHashTagsForNewThread(resultTagsList, getActualPost(currentPostState))
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Utils.setConsiderPostLength(false)
        Utils.setTag(null)
        Utils.setPost(null)
        Utils.setChipClosed(false)
        platform = context?.getString(R.string.instagram_id).toString()

        val view = inflater.inflate(R.layout.blank_fragment, container, false)

        if (!Utils.isTiktokMaxLengthSet() && Utils.getTabPosition() == 0) {
            val maxLength = 100
            val tmp = view?.findViewById<EditText>(R.id.editTextPost)
            if (tmp != null) {
                tmp.filters = arrayOf<InputFilter>(
                    InputFilter.LengthFilter(maxLength)
                )
                Utils.setTiktokMaxLengthSet(true)
            }
        }

        val postLength = view.findViewById<SwitchCompat>(R.id.post_length)
        val postText: EditText = view.findViewById(R.id.editTextPost)
        val postDisplay: SwitchCompat = view.findViewById(R.id.post_display)
        val submitButton: Button = view.findViewById(R.id.submit_button)

        postLength.setOnCheckedChangeListener { _, considerPostLength ->
            if (considerPostLength) {
                postText.visibility = View.VISIBLE
                postDisplay.visibility = View.VISIBLE
            } else {
                postText.visibility = View.GONE
                postDisplay.visibility = View.GONE
            }
        }

        submitButton.setOnClickListener {
            if (platform.isEmpty()) {
                Toast.makeText(view?.context, getString(R.string.no_hashtags_found), Toast.LENGTH_SHORT)
                    .show()
            } else if (initialCheck()) {
                progressDialog = ProgressDialog(activity)

                if ((Utils.isConsiderPostLength() != postLength.isChecked) || (post_length.isChecked && Utils.getPost() == null) || (Utils.getTag() == null) || (((Utils.getPost() != null) || editTextPost.text.isNotEmpty()) && (Utils.getPost() != editTextPost.text.toString())) || (Utils.getTag() != editTextTag.text.toString()) || Utils.isChipClosed()) {
                    MyAsyncTask().execute()

                    Utils.setConsiderPostLength(postLength.isChecked)
                    Utils.setTag(editTextTag.text.toString())
                    Utils.setChipClosed(false)
                }
            }
        }

        val copyToClipboardManager: Button = view.findViewById(R.id.copyToClipboard)
        copyToClipboardManager.setOnClickListener {
            val clipboard =
                activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                "text",
                view.findViewById<TextView>(R.id.resultWithHashTags).text.toString()
            )
            clipboard.setPrimaryClip(clip)

            Toast.makeText(activity, getString(R.string.result_copied), Toast.LENGTH_SHORT).show()
        }

        postDisplay.setOnCheckedChangeListener { _, _ ->
            if (isResultCalculated) {
                updateResultWithHashTags(
                    resultTagsList,
                    getActualPost(editTextPost.text.toString())
                )
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val inflater: LayoutInflater = LayoutInflater.from(view?.context)
        viewModel = ViewModelProviders.of(this).get(BlankViewModel::class.java)
        if (savedInstanceState != null) {
            val textViewText = savedInstanceState.getString("RESULT")
            if (textViewText != null) {
                if (textViewText.isNotEmpty()) {
                    val nameView = view?.findViewById(R.id.resultWithHashTags) as TextView
                    nameView.text = textViewText
                    nameView.visibility = View.VISIBLE
                    copyToClipboard.visibility = View.VISIBLE

                    val chips = savedInstanceState.getStringArrayList("CHIPS")

                    if (chips != null) {
                        val addToResult = resultTagsList.isEmpty()
                        for (tag in chips) {
                            if (addToResult) {
                                resultTagsList.add(tag)
                            }

                            val chip: Chip =
                                inflater.inflate(R.layout.chip_item, null, false) as Chip
                            chip.text = tag
                            chip.setOnCloseIconClickListener {
                                chipGroup.removeView(chip)
                                Utils.setChipClosed(true)
                                chips.remove(tag)
                                resultTagsList.remove(tag)
                                updateResultWithHashTags(
                                    chips,
                                    getActualPost(editTextPost.text.toString())
                                )

                                if (resultTagsList.isEmpty()) {
                                    Toast.makeText(
                                        view?.context,
                                        getString(R.string.all_hashtags_removed),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            chipGroup.addView(chip)
                        }
                    }
                    chipGroup.visibility = View.VISIBLE
                    div2.visibility = View.VISIBLE
                }
            }
        }
        // TODO: Use the ViewModel
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val nameView = view?.findViewById<TextView>(R.id.resultWithHashTags)?.text.toString()
        if (nameView.isNotEmpty()) {
            outState.putString("RESULT", nameView)
            outState.putStringArrayList("CHIPS", resultTagsList)
        }
        super.onSaveInstanceState(outState)
    }

    inner class MyAsyncTask : AsyncTask<Void, Int, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(view?.context)
            progressDialog.show()
            progressDialog.setContentView(R.layout.progress_dialog)
            progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            progressDialog.setCancelable(false)
        }

        override fun doInBackground(vararg params: Void): String? {
            Utils.setChipsFromGroup(ArrayList())
            resultTagsList.clear()
            updateResultWithHashTagsForNewThread(
                resultTagsList,
                getActualPost(editTextPost.text.toString())
            )

            view?.let { generateResultTags(it) }
            isResultCalculated = true

            if (editTextPost.text.isEmpty()) {
                Utils.setPost(null)
            } else {
                Utils.setPost(editTextPost.text.toString())
            }

            activity?.let { it1 -> hideKeyboard(it1) }

            return "success"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressDialog.dismiss()

            copyToClipboard.visibility =
                if (Utils.isCopyToClipboardVisible()) View.VISIBLE else View.GONE
            resultWithHashTags.visibility =
                if (Utils.isResultWithHashtagsVisible()) View.VISIBLE else View.GONE
            div2.visibility =
                if (Utils.isDiv2Visible()) View.VISIBLE else View.GONE
            chipGroup.visibility =
                if (Utils.isChipGroupVisible()) View.VISIBLE else View.GONE
            resultWithHashTags.text = Utils.getResultWithHashtagsText()

            addChipsToGroup()

            if (Utils.getErrorMessage() != null) {
                Toast.makeText(view?.context, Utils.getErrorMessage(), Toast.LENGTH_SHORT)
                    .show()
                Utils.setErrorMessage(null)
            }
        }
    }
}
