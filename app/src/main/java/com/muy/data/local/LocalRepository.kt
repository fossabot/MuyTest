package com.muy.data.local

import androidx.lifecycle.LiveData
import androidx.room.EmptyResultSetException
import com.muy.App
import com.muy.data.database.MuyDatabase
import com.muy.data.dto.EmployeeItem
import com.muy.data.dto.EmployeerResponse
import com.muy.data.dto.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import kotlin.coroutines.CoroutineContext


class LocalRepository(
        override val coroutineContext: CoroutineContext,
        private val database: MuyDatabase
) : LocalSource, CoroutineScope {

        private var call:Call<EmployeerResponse>?=null
        override fun getAllCompanies(): LiveData<EmployeerResponse> {
                return database.PostDao().getAllEmployee()
        }

        override fun getNewEmployees(): LiveData<List<News>> {
                return database.PostDao().getAllNewEmployee()
        }

        override fun getAllNewObjectEmployee(): LiveData<List<EmployeeItem>> {
                return database.PostDao().getAllNewObjectEmployee()

        }

        override fun insertNewid(id: Int) {
                GlobalScope.launch(Dispatchers.IO) {
                        val newInDb = try {
                                database.PostDao().getNew(id)
                        } catch (e: EmptyResultSetException) {
                                null
                        }
                        if(newInDb == null){
                                var employenew = News(id)
                                database.PostDao().insertNew(employenew)
                        }else{

                                database.PostDao().deleteNewById(id)
                        }
                }
        }


        override fun insertCompanies(list : EmployeerResponse) {
                GlobalScope.launch(Dispatchers.IO) {
                        database.PostDao().insert(list)
                }
        }


}