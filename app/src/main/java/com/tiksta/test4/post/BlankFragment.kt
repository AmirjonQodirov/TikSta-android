package com.tiksta.test4.post

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.tiksta.test4.R
import com.tiksta.test4.post.data.DataBaseHandler
import kotlinx.android.synthetic.main.activity_post_filling.*

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
            val context = activity
            var db = context?.let { it1 -> DataBaseHandler(it1) }
            val tabPosition = Utils.getTabPosition()

            var platform = "instagram_id"
            if (tabPosition == 0) {
                platform = "tiktok_id"
            }
            var data = db?.readData(editTextTag.text.toString(),platform)


//            if (data == null) {
//                // TODO
//            } else {
//
//                for (i in 0 until data.size) {
//
//                    tagRes.append(data[i] + "\n")
//                }
//                tagRes.append("Usyo")
//            }
            val recyclerView = view.findViewById(R.id.list) as RecyclerView
            val adapter = DataAdapter(activity, data)
            recyclerView.adapter = adapter
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BlankViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
