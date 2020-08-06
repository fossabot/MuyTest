package com.muy.ui.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.muy.R
import com.zemoga.ui.base.BaseActivity


abstract class BaseFragment<T : ViewDataBinding> : Fragment() {


    private lateinit var viewDataBinding: T

    fun getViewDataBinding(): T {
        return viewDataBinding
    }

    abstract val layoutId: Int

    abstract fun observeViewModel()




    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
       // navController = Navigation.findNavController(view)
        getViewDataBinding().lifecycleOwner = this
        getViewDataBinding().executePendingBindings()
        observeViewModel()
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        return viewDataBinding.root
    }



    fun setTitle(title: String) {
        val actionBar = (activity as BaseActivity).supportActionBar
        if (actionBar != null) {
            val titleTextView = activity?.findViewById<TextView>(R.id.txt_toolbar_title)
            if (title.isNotEmpty()) {
                titleTextView?.text = title
            }
        }
    }



}
