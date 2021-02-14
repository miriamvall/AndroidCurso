package com.curso.androidcurso.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.curso.androidcurso.R
import com.curso.androidcurso.adapters.TabsAdapter
import com.google.android.material.tabs.TabLayout

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentTabs : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tabs, container, false)

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        tabLayout.setupWithViewPager(viewPager)

        val adapter = TabsAdapter(
                childFragmentManager,
                listOf(FragmentStats(), FragmentGlobalStats()))
        viewPager.adapter = adapter

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FragmentTabs().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}