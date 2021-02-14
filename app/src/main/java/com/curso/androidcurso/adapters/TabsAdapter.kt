package com.curso.androidcurso.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabsAdapter (
    fragmentManager: FragmentManager,
    private val tabs: List<Fragment>

        ): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return tabs[position]
    }

    override fun getCount(): Int {
        return tabs.count()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0){
            "Local"
        } else {
            "Global"
        }
    }
}