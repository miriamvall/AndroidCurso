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
import com.curso.androidcurso.adapters.StatsAdapter
import com.curso.androidcurso.model.Partida
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val STATS_DETAILS = 0


class FragmentStats : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StatsAdapter
    private lateinit var statsEmptyView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        statsEmptyView = view.findViewById<LinearLayout>(R.id.emptyStatsView)

        adapter = StatsAdapter()

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
                DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )

        updateEmptyView()

        return view
    }

    private fun updateEmptyView() {
        if(adapter.dataset.isEmpty()) {
            statsEmptyView.visibility = View.VISIBLE
        } else {
            statsEmptyView.visibility = View.GONE
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentStats().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}