package com.muy.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.muy.data.database.EmployeesTypeConverter
import java.math.BigInteger

@Entity(tableName = "company")
@TypeConverters(
	EmployeesTypeConverter::class)
data class EmployeerResponse(
	@PrimaryKey(autoGenerate = true)
	val id: Int?,
	val address: String,
	val company_name: String,

	val employees: List<EmployeesItem>
)

@Entity(tableName = "employeeBoss")
data class EmployeesItem(
	val name: String?,
	@PrimaryKey
	val id: Int?,
	val position: String?,

	val employees: List<EmployeeItem>,
	val wage: BigInteger?
)

@Entity(tableName = "employees")
data class EmployeeItem(
	val name: String,
	@PrimaryKey
	val id: Int?
)



@Entity(tableName = "news")
data class News(
	@PrimaryKey
	val id: Int?
)

