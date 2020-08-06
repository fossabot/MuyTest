package com.muy.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.muy.data.dto.EmployeeItem
import com.muy.data.dto.EmployeerResponse
import com.muy.data.dto.News
import io.reactivex.Single

@Dao
interface EmployeersDAO {
    @Query("SELECT * FROM company")
    fun getAllEmployee() : LiveData<EmployeerResponse>

    @Query("SELECT * FROM news ")
    fun getAllNewEmployee() : LiveData<List<News>>

    @Query("SELECT * FROM employees where id IN (SELECT id FROM news)")
    fun getAllNewObjectEmployee() : LiveData<List<EmployeeItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insert(posts: EmployeerResponse)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNew(posts: News)

    @Query("DELETE FROM news WHERE id = :id")
    fun deleteNewById(id: Int)

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getNew(id: Int) : News
}