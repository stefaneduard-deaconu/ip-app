package com.example.ipapp.ui.docs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ipapp.R

class DocsFragment : Fragment() {

    private lateinit var docsViewModel: DocsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        docsViewModel =
                ViewModelProviders.of(this).get(DocsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_docs, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        docsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
