package com.muy.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muy.data.dto.EmployeesItem
import com.muy.data.dto.EmployeerResponse
import com.muy.data.service.OperationCallback
import com.muy.data.DataSource
import com.muy.data.dto.EmployeeItem
import com.muy.data.dto.News


class AllEmployeesViewModel(private val repository: DataSource):ViewModel() {

    private val _employeers = MutableLiveData<List<EmployeesItem>>().apply { value = emptyList() }
    val employeers: LiveData<List<EmployeesItem>> = _employeers


    val onClicked: (id: Int) -> Unit = { id ->
        repository.insertNewid(id)
    }

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _isEmptyList=MutableLiveData<Boolean>()
    val isEmptyList:LiveData<Boolean> = _isEmptyList


    fun getpost (): LiveData<EmployeerResponse> {

        return repository.getAllCompanies()
    }

    fun getNews (): LiveData<List<News>> {

        return repository.getNewEmployees()
    }

    fun loadMuseums(){
        _isViewLoading.postValue(true)
        repository.retrieveEmployeers(object:
            OperationCallback<EmployeerResponse> {
            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue( error)
            }

            override fun onSuccess(data: EmployeerResponse?) {
                _isViewLoading.postValue(false)

                if(data!=null){
                    if(data.employees.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _employeers.value= data.employees
                        repository.insertCompanies(data)

                    }
                }
            }
        })
    }

}