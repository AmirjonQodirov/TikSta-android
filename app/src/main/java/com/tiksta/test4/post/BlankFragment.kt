package com.tiksta.test4.post

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tiksta.test4.R

class BlankFragment : Fragment() {
    private lateinit var viewModel: BlankViewModel

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.blank_fragment, container, false)

        val postLength = view.findViewById<Switch>(R.id.post_length)
        val postText: EditText = view.findViewById(R.id.editTextPost)
        val tag: EditText = view.findViewById(R.id.editTextTag)
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
            val tabPosition = Utils.getTabPosition()
            val result: TextView = view.findViewById(R.id.result)

            if (tabPosition == 0) {
                result.text = getString(R.string.tiktok)
            } else {
                result.text = getString(R.string.instagram)
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BlankViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
