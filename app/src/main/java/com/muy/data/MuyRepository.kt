package com.muy.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.EmptyResultSetException
import com.muy.App
import com.muy.data.dto.EmployeeItem
import com.muy.data.dto.EmployeerResponse
import com.muy.data.dto.News
import com.muy.data.local.LocalSource
import com.muy.data.remote.RemoteSource
import com.muy.data.service.OperationCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call

const val TAG="CONSOLE"

class MuyRepository(
    private val remoteRepository: RemoteSource,
    private val localRepository: LocalSource
) : DataSource {

    private var call:Call<EmployeerResponse>?=null
    private val postLiveData = MutableLiveData<EmployeerResponse>()
    override val postInfoData: MutableLiveData<EmployeerResponse> =
        postLiveData

    override fun retrieveEmployeers(callback: OperationCallback<EmployeerResponse>) {
        remoteRepository.retrieveEmployeers(callback)
    }

    override fun getAllCompanies(): LiveData<EmployeerResponse> {
        return localRepository.getAllCompanies()
    }

    override fun getNewEmployees(): LiveData<List<News>> {
        return localRepository.getNewEmployees()
    }

    override fun getAllNewObjectEmployee(): LiveData<List<EmployeeItem>> {
        return localRepository.getAllNewObjectEmployee()

    }

    override fun insertNewid(id: Int) {
        localRepository.insertNewid(id)
    }


    override fun insertCompanies(list : EmployeerResponse) {
        localRepository.insertCompanies(list)
    }

    override fun cancel() {
      remoteRepository.cancel()
    }


}