package com.muy.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muy.data.DataSource
import com.muy.ui.fragments.AllEmployeesViewModel

class ViewModelFactory(private val repository: DataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return AllEmployeesViewModel(repository) as T
    }
}