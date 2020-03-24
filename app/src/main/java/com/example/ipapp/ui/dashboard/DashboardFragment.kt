package com.example.ipapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ipapp.MainActivity
import com.example.ipapp.R

class DashboardFragment : Fragment() {

    // variable for the MainActivity:
    private lateinit var mainActivity: MainActivity
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        mainActivity = activity as MainActivity
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setting up the recycler view
        mainActivity.viewManager = LinearLayoutManager(mainActivity)
        mainActivity.viewAdapter = com.example.ipapp.ImageAdapter(mainActivity.bitmaps)

        mainActivity.recyclerView = mainActivity.findViewById<RecyclerView>(R.id.recycler_view)
        mainActivity.recyclerView.layoutManager = mainActivity.viewManager
        mainActivity.recyclerView.adapter = mainActivity.viewAdapter
//            .apply {
////            // use this setting to improve performance if you know that changes
////            // in content do not change the layout size of the RecyclerView
////            setHasFixedSize(true)
//            // use a linear layout manager
//            layoutManager = viewManager
//            // specify an viewAdapter (see also next example)
//            adapter = viewAdapter
//        }
    }
}
