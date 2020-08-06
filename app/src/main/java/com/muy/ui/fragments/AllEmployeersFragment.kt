package com.muy.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.muy.App
import com.muy.R
import com.muy.data.dto.EmployeerResponse
import com.muy.data.dto.EmployeesItem
import com.muy.data.dto.News
import com.muy.databinding.FragmentWorkersBinding
import com.muy.ui.MainActivity
import com.muy.ui.adapter.AllEmployeesAdapter
import com.muy.ui.base.base.BaseFragment


class AllEmployeersFragment : BaseFragment<FragmentWorkersBinding>(){

    private lateinit var viewModel: AllEmployeesViewModel
    private lateinit var dialog :Dialog
    lateinit var adapter: AllEmployeesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, App.getInjection()!!.provideViewModelFactory()).get(
            AllEmployeesViewModel::class.java)
        viewModel.loadMuseums()
        adapter = AllEmployeesAdapter(viewModel.onClicked,requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);
        var recycler = getViewDataBinding().recyclerView
        dialog= onCreateDialog()!!
        recycler.layoutManager= LinearLayoutManager(activity)
        recycler.setItemViewCacheSize(10)
        recycler.adapter= adapter

        recycler.setHasFixedSize(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        val searchView = SearchView(((context as MainActivity).supportActionBar?.themedContext ?: context)!!)

       val searchItem = menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        val addActionMenuItem = menu.findItem(R.id.filter)

        if (searchItem is MenuItem) {
            searchItem.setOnActionExpandListener(object :
                MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                    addActionMenuItem.isVisible = false
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                    activity?.invalidateOptionsMenu()
                    return true
                }
            })
        }


        search(searchView)

        super.onCreateOptionsMenu(menu, inflater)

    }

    fun onCreateDialog(): Dialog? {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        builder.setTitle(getString(R.string.filter_title))
        builder.setMultiChoiceItems(
            R.array.titles, null
        ) { _, _, _ ->
            adapter.filter()
            dialog.dismiss()
        }
        return builder.create()
    }


    private fun search(searchView: SearchView) {


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false;
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.search(newText.toString())
                return true;
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {


                return true
            }
            R.id.filter ->{
                Log.d("hola1","f")
                dialog.show()
                return true
            }

            else -> {
            }
        }
        return false
    }


    override val layoutId: Int
        get() = R.layout.fragment_workers


    override fun observeViewModel() {
        viewModel.employeers.observe(this,renderMuseums)
        viewModel.isViewLoading.observe(this,isViewLoadingObserver)
        viewModel.onMessageError.observe(this,onMessageErrorObserver)
        viewModel.getpost().observe(this,ErrorObserver)
        viewModel.getNews().observe(this,ChangeNews)

    }

    private val isViewLoadingObserver= Observer<Boolean> {
        val visibility=if(it)View.VISIBLE else View.GONE
        getViewDataBinding().progressBar.visibility= visibility
    }
    private val renderMuseums= Observer<List<EmployeesItem>> {
        Log.v(MainActivity.TAG, "data updated $it")
        getViewDataBinding().layoutError.root.visibility=View.GONE
        getViewDataBinding().layoutEmpty.root.visibility=View.GONE
    }

    private val ErrorObserver= Observer<EmployeerResponse> {
        Log.v(MainActivity.TAG, "dataase $it")
        if(it != null){
            adapter.update(it.employees)
            getViewDataBinding().layoutEmpty.root.visibility=View.GONE
            getViewDataBinding().layoutError.root.visibility=View.GONE
        }

    }


    private val ChangeNews= Observer<List<News>> {
        Log.v(MainActivity.TAG, "dataase $it")
        adapter.updateNews(it)
        getViewDataBinding().layoutEmpty.root.visibility=View.GONE
        getViewDataBinding().layoutError.root.visibility=View.GONE
    }



    private val onMessageErrorObserver= Observer<Any> {
        Log.v(MainActivity.TAG, "onMessageError $it")
        getViewDataBinding().layoutError.root.visibility=View.VISIBLE
        getViewDataBinding().layoutEmpty.root.visibility=View.GONE
    }

    private val emptyListObserver= Observer<Boolean> {
        Log.v(MainActivity.TAG, "emptyListObserver $it")
        getViewDataBinding().layoutEmpty.root.visibility=View.VISIBLE
        getViewDataBinding().layoutError.root.visibility=View.GONE
    }




}

