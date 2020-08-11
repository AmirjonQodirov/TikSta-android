package com.tiksta.test4.post

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tiksta.test4.R
import com.tiksta.test4.post.data.DataBaseHandler
import kotlinx.android.synthetic.main.activity_post_filling.*

class BlankFragment : Fragment() {
    private lateinit var viewModel: BlankViewModel

    private fun generateResultTags(view: View) {
        val chipGroup: ChipGroup = view.findViewById(R.id.chipGroup)
        val inflater: LayoutInflater = LayoutInflater.from(view.context)

        chipGroup.removeAllViews()

        if (editTextPost.length() < 0) {
            editTextTag.error = "This field is required!"
        }


        val db = context?.let { it1 -> DataBaseHandler(it1) }

        if (db == null) {
            Toast.makeText(view.context, "Can't connect to database :(", Toast.LENGTH_LONG).show()
            return
        }

        val tabPosition = Utils.getTabPosition()

        var platform = "instagram_id"
        if (tabPosition == 0) {
            platform = "tiktok_id"
        }

        val data = db.readData(editTextTag.text.toString(), platform)

        if (data.isNotEmpty()) {
            for (currentTag in data) {
                val chip: Chip = inflater.inflate(R.layout.chip_item, null, false) as Chip
                chip.text = currentTag
                chip.setOnCloseIconClickListener {
                    chipGroup.removeView(chip)
                }

                chipGroup.addView(chip)
            }
        } else {
            Toast.makeText(view.context, "No hashtags found!", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.blank_fragment, container, false)

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

        val postLength = view.findViewById<Switch>(R.id.post_length)
        val postText: EditText = view.findViewById(R.id.editTextPost)
        val postDisplay: Switch = view.findViewById(R.id.post_display)

        postLength.setOnCheckedChangeListener { _, considerPostLength ->
            if (considerPostLength) {
                postText.visibility = View.VISIBLE
                postDisplay.visibility = View.VISIBLE
            } else {
                postText.visibility = View.GONE
                postDisplay.visibility = View.GONE
            }
        }

        val submitButton: Button = view.findViewById(R.id.submit_button)

        submitButton.setOnClickListener {
            generateResultTags(view)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BlankViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
