package com.muy.data.remote

import com.muy.data.dto.EmployeerResponse
import com.muy.data.service.OperationCallback

interface RemoteSource {
    fun retrieveEmployeers(callback: OperationCallback<EmployeerResponse>)
    fun cancel()

}
