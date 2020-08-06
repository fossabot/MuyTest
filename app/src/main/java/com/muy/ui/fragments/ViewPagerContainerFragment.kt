package com.muy.ui.fragments
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.muy.R
import com.muy.databinding.FragmentViewMainBinding
import com.muy.ui.adapter.SectionsPagerAdapter
import com.muy.ui.base.base.BaseFragment


class ViewPagerContainerFragment : BaseFragment<FragmentViewMainBinding>() {

    private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TabLayout
        val tabLayout =  getViewDataBinding().tabs
        // ViewPager2
        val viewPager =   getViewDataBinding().viewPager

        viewPager.adapter = SectionsPagerAdapter(
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "${resources.getString(TAB_TITLES[position])}"

        }.attach()

//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                if(position == 1)
//                    getViewDataBinding().fab.visibility = View.GONE
//                else
//                    getViewDataBinding().fab.visibility = View.VISIBLE
//            }
//        })
//
//
//        getViewDataBinding().fab.setOnClickListener { view ->
//            //viewmodel.deleteClickAll()
//        }

    }

    override fun onDestroyView() {

        val viewPager2 = getViewDataBinding().viewPager
        viewPager2.let {
            it.adapter = null
        }

        super.onDestroyView()
    }

    override val layoutId: Int
        get() = R.layout.fragment_view_main


    override fun observeViewModel() {
    }


}
