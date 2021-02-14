package com.curso.androidcurso.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.curso.androidcurso.R
import com.curso.androidcurso.model.StatsApi
import com.curso.androidcurso.adapters.StatsAdapter
import com.curso.androidcurso.model.Partida


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentGlobalStats : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StatsAdapter
    private lateinit var statsEmptyView: View
    private lateinit var errorLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_global_stats, container, false)

        statsEmptyView = view.findViewById<LinearLayout>(R.id.emptyGlobalStatsView)
        errorLayout = view.findViewById<LinearLayout>(R.id.errorStatsView)

        adapter = StatsAdapter()

        recyclerView = view.findViewById(R.id.recyclerViewGlobal)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )

        StatsApi.getAllRecords(this::onSuccess, this::onError)

        updateEmptyView()

        return view
    }

    private fun updateEmptyView() {
        if(adapter.dataset.isEmpty()) {
            statsEmptyView.visibility = View.VISIBLE
            errorLayout.visibility = View.GONE
        } else {
            statsEmptyView.visibility = View.GONE
            errorLayout.visibility = View.GONE
        }
    }

    private fun onSuccess(data: List<Partida>) {
        activity!!.runOnUiThread {
            adapter.setData(data)
            updateEmptyView()
        }
    }

    private fun onError() {
        activity!!.runOnUiThread {
            println("error al hacer GET")
            adapter.setData(emptyList())
            errorLayout.visibility = View.VISIBLE
            statsEmptyView.visibility = View.GONE

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FragmentGlobalStats().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}