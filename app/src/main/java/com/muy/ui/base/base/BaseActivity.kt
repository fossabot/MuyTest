package com.zemoga.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muy.ui.base.base.listeners.ActionBarView


abstract class BaseActivity : AppCompatActivity(), ActionBarView {

    protected abstract fun initializeViewModel()
    abstract fun observeViewModel()
    protected abstract fun initViewBinding()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initializeViewModel()
        observeViewModel()
    }



    override fun setUpIconVisibility(visible: Boolean) {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(visible)
    }




}
