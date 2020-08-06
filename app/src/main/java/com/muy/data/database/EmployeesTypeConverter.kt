package com.muy.data.database

import androidx.room.TypeConverter
import com.muy.data.dto.EmployeesItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class  EmployeesTypeConverter{

    var gson = Gson()

    @TypeConverter
    fun stringToEmployeesItemList(data: String?): List<EmployeesItem?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object :
            TypeToken<List<EmployeesItem?>?>() {}.type
        return gson.fromJson<List<EmployeesItem?>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToEmployeesItem(someObjects: List<EmployeesItem?>?): String? {
        return gson.toJson(someObjects)
    }
}