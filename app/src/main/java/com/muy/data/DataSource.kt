package com.muy.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.muy.data.dto.EmployeeItem
import com.muy.data.dto.EmployeerResponse
import com.muy.data.dto.News
import com.muy.data.service.OperationCallback

interface DataSource {

    fun retrieveEmployeers(callback: OperationCallback<EmployeerResponse>)
    fun getAllCompanies():LiveData<EmployeerResponse>
    fun getNewEmployees():LiveData<List<News>>
    fun getAllNewObjectEmployee():LiveData<List<EmployeeItem>>

    fun insertNewid(id : Int)

    fun insertCompanies(list : EmployeerResponse)
    fun cancel()
    val postInfoData: MutableLiveData<EmployeerResponse>

}