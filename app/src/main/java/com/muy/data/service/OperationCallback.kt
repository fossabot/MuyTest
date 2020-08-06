package com.muy.data.service

interface OperationCallback<T> {
    fun onSuccess(data:T?)
    fun onError(error:String?)
}