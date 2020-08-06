package com.muy.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.muy.R
import com.muy.databinding.ActivityContentBinding
import com.zemoga.ui.base.BaseActivity

class MainActivity : BaseActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityContentBinding



    companion object {
        const val TAG= "CONSOLE"
    }

    override fun initializeViewModel() {

    }

    override fun observeViewModel() {

    }

    override fun initViewBinding() {
        binding = ActivityContentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    /**
     //Consider this, if you need to call the service once when activity was created.
        Log.v(TAG,"savedInstanceState $savedInstanceState")
        if(savedInstanceState==null){
            viewModel.loadMuseums()
        }
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbarLayout.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false);

        val navController = findNavController(R.id.nav_host_fragment)


        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)

    }


    //viewmodel
    /**
        //Consider this if ViewModel class don't require parameters.
        viewModel = ViewModelProviders.of(this).get(MuseumViewModel::class.java)

        //if you require any parameters to  the ViewModel consider use a ViewModel Factory
        //viewModel = ViewModelProviders.of(this,ViewModelFactory(Injection.providerRepository())).get(MuseumViewModel::class.java)
        viewModel = ViewModelProvider(this,Injection.provideViewModelFactory()).get(MuseumViewModel::class.java)

        //Anonymous observer implementation
        viewModel.museums.observe(this,Observer<List<Museum>> {
            Log.v("CONSOLE", "data updated $it")
            adapter.update(it)
        })
     */
    private fun setupViewModel(){


    }


}
