package com.muy.data.local

import androidx.lifecycle.LiveData
import com.muy.data.dto.EmployeeItem
import com.muy.data.dto.EmployeerResponse
import com.muy.data.dto.News

interface LocalSource {
    fun getAllCompanies(): LiveData<EmployeerResponse>
    fun getNewEmployees(): LiveData<List<News>>
    fun getAllNewObjectEmployee(): LiveData<List<EmployeeItem>>

    fun insertNewid(id : Int)

    fun insertCompanies(list : EmployeerResponse)

}
