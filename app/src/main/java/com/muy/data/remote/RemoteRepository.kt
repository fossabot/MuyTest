package com.muy.data.remote;

import android.util.Log
import com.muy.data.TAG
import com.muy.data.dto.EmployeerResponse
import com.muy.data.service.ApiClient
import com.muy.data.service.OperationCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository(private val apiCLient: ApiClient.ServicesApiInterface) : RemoteSource {

        private var call:Call<EmployeerResponse>?=null

        override fun retrieveEmployeers(callback: OperationCallback<EmployeerResponse>) {

                call =apiCLient.museums()
                call?.enqueue(object : Callback<EmployeerResponse> {
                        override fun onFailure(call: Call<EmployeerResponse>, t: Throwable) {
                                callback.onError(t.message)
                        }

                        override fun onResponse(call: Call<EmployeerResponse>, response: Response<EmployeerResponse>) {
                                response.body()?.let {
                                        if(response.isSuccessful ){
                                                Log.v(TAG, "data ${it.employees}")
                                                callback.onSuccess(it)
                                        }else{
                                                callback.onError(it.address)
                                        }
                                }
                        }
                })
        }

        override fun cancel() {
                call?.let {
                        it.cancel()
                }
        }


}