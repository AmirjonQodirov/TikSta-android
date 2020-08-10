package com.tiksta.test4

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast

class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }

    private lateinit var viewModel: BlankViewModel

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.blank_fragment, container, false)

        val checkBox = view.findViewById<Switch>(R.id.post_length)
        val ed: EditText = view.findViewById(R.id.editTextPost)
        val edTag: EditText = view.findViewById(R.id.editTextTag)
        val sw: Switch = view.findViewById(R.id.post_display)
        checkBox.setOnCheckedChangeListener { _, b ->
            if (b) {
//                Toast.makeText(this, "Checked", Toast.LENGTH_LONG).show()
                ed.visibility = View.VISIBLE
                sw.visibility = View.VISIBLE
//                Toast.makeText(this, edTag.text, Toast.LENGTH_SHORT).show()
            } else {
//                Toast.makeText(this, "UnChecked", Toast.LENGTH_LONG).show()
                ed.visibility = View.GONE
                sw.visibility = View.GONE
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