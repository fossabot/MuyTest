package com.muy.ui.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.muy.ui.fragments.AllEmployeersFragment
import com.muy.ui.fragments.NewEmployeers

class SectionsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fm,lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllEmployeersFragment()
            1 -> NewEmployeers()
            else -> AllEmployeersFragment()
        }

    }
}