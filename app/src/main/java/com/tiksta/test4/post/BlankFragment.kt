package com.tiksta.test4.post

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tiksta.test4.R
import com.tiksta.test4.post.data.DataBaseHandler
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

    private fun getActualPost(post: String): String {
        return if (post_length.isChecked && post_display.isChecked && (post.isNotEmpty() && !post[post.length - 1].isWhitespace())) {
            "$post\n"
        } else if (post_length.isChecked && post_display.isChecked && (post.isEmpty() || post[post.length - 1].isWhitespace())) {
            post
        } else {
            ""
        }
    }

    private fun updateResultWithHashTags(resultTagsList: ArrayList<String>, post: String) {
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

        if (resultTagsList.isEmpty() && !post_display.isChecked) {
            copyToClipboard.visibility = View.GONE
            resultWithHashTags.visibility = View.GONE
            div2.visibility = View.GONE
        }

        return
    }

    private fun generateResultTags(view: View) {
        val chipGroup: ChipGroup = view.findViewById(R.id.chipGroup)
        val inflater: LayoutInflater = LayoutInflater.from(view.context)

        chipGroup.removeAllViews()

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
        } else if (platform == "instagram_id" && post_length.isChecked && postLength >= 2199) {
            editTextPost.error = "Your post is too long!"
            return
        } else if (platform == "tiktok_id" && post_length.isChecked && postLength >= 99) {
            editTextPost.error = "Your post is too long!"
            return
        }


        val db = context?.let { it1 -> DataBaseHandler(it1) }

        if (db == null) {
            Toast.makeText(view.context, "Can't connect to database :(", Toast.LENGTH_LONG).show()
            return
        }

        val tags = editTextTag.text.toString() + " "
        var currentTag = StringBuilder()
        val allTags: ArrayList<String> = ArrayList()

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

        val list: ArrayList<String> = ArrayList()
        val hasTag: TreeMap<String, Boolean> = TreeMap()

        print("Has: ")

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
        println()

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
            Toast.makeText(view.context, "You already have 30/30 hashtags!", Toast.LENGTH_LONG)
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
            Toast.makeText(view.context, "No hashtags found!", Toast.LENGTH_LONG).show()
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
                Toast.LENGTH_LONG
            ).show()
            return
        }

        for (tag in resultTagsList) {
            val chip: Chip = inflater.inflate(R.layout.chip_item, null, false) as Chip
            chip.text = tag
            chip.setOnCloseIconClickListener {
                chipGroup.removeView(chip)

                if (resultTagsList.size == 1) {
                    Toast.makeText(
                        view.context,
                        "All hashtags have been removed!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                resultTagsList.remove(tag)

                updateResultWithHashTags(resultTagsList, getActualPost(currentPostState))
            }

            chipGroup.addView(chip)
        }

        updateResultWithHashTags(resultTagsList, getActualPost(currentPostState))
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.blank_fragment, container, false)
//        Utils.setPostActivityView(view)

        if (!Utils.isTiktokMaxLengthSet() && Utils.getTabPosition() == 0) {
            Utils.setTiktokMaxLengthSet(true)
            val maxLength = 100

            val tmp = view.findViewById<EditText>(R.id.editTextPost)
            if (tmp != null) {
                tmp.filters = arrayOf<InputFilter>(
                    InputFilter.LengthFilter(maxLength)
                )
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

            if (isResultCalculated) {
                submitButton.performClick()
            }
        }


        submitButton.setOnClickListener {
//            if (!Utils.isDatabaseCreated()) {
//                print("cd111111111111111")
//                Toast.makeText(view.context, "Database is loading", Toast.LENGTH_LONG).show()
//                edittt.visibility = View.VISIBLE
//            }
            edittt.visibility = View.GONE
            resultTagsList.clear()
            updateResultWithHashTags(
                resultTagsList,
                getActualPost(editTextPost.text.toString())
            )
            generateResultTags(view)
            isResultCalculated = true
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

            Toast.makeText(activity, "Result copied to clipboard!", Toast.LENGTH_LONG).show()
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
            val textViewText = savedInstanceState?.getString("RESULT")
            if (textViewText != null) {
                if (textViewText.isNotEmpty()) {
                    val nameView = view?.findViewById(R.id.resultWithHashTags) as TextView
                    nameView.text = textViewText
                    nameView.visibility = View.VISIBLE
                    copyToClipboard.visibility = View.VISIBLE

                    var chips = savedInstanceState?.getStringArrayList("CHIPS")

                    if (chips != null) {
                        var addToResult = resultTagsList.isEmpty()
                        for (tag in chips) {
                            if (addToResult) {
                                resultTagsList.add(tag)
                            }

                            val chip: Chip =
                                inflater.inflate(R.layout.chip_item, null, false) as Chip
                            chip.text = tag
                            chip.setOnCloseIconClickListener {
                                chipGroup.removeView(chip)

                                if (chips.size == 1) {
                                    Toast.makeText(
                                        view?.context,
                                        "All hashtags have been removed!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                chips.remove(tag)
                                resultTagsList.remove(tag)

                                updateResultWithHashTags(
                                    chips,
                                    getActualPost(editTextPost.text.toString())
                                )
                            }


                            chipGroup.addView(chip)
                        }
                    }
                    chipGroup.visibility = View.VISIBLE
                    div2.visibility = View.VISIBLE
                    edittt.visibility = View.GONE
                    //Restore the fragment's state here
                }
            }
        }

        // TODO: Use the ViewModel
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val nameView = view?.findViewById<TextView>(R.id.resultWithHashTags)?.text.toString()
        if (nameView.isNotEmpty()) {
            outState.putString("RESULT", nameView)
            println("saved = = =  $nameView")

//            if(resultTagsList.isNotEmpty()){
            outState.putStringArrayList("CHIPS", resultTagsList)
//            }
        }
        super.onSaveInstanceState(outState)
    }

}

/*
#a#b#c#d#e#f#g#h#j#h#f#d#d#d#d#d#c#c#c#c#c#c#c#C#d#d#s#G#g3#s#g#a#r#s#a#a#d#a#a#a#a#a#a#a#a#a#a#a
a#a#b#c#d#e#f#g#h#j#h#f#d#d#d#d#d#c#c#c#c#c#c#c#C#d#d#s#G#g3#s#g#a#r#s#a#a#d#a#a#a#a#a#a#a#a#a#a#a
#sky #vsco #music #style #smile #igers #girls #artist #repost #summer #selfie #sunset #design
 */
