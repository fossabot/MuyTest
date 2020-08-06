package com.muy.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muy.data.dto.EmployeeItem
import com.muy.data.dto.EmployeerResponse
import com.muy.data.dto.News

@Database(entities = [EmployeerResponse::class, EmployeeItem::class,News::class], version = 5, exportSchema = false)
abstract class MuyDatabase : RoomDatabase() {

    abstract fun PostDao(): EmployeersDAO

    companion object {
        private const val NUMBER_OF_THREADS = 4

        @Volatile
        private var INSTANCE: MuyDatabase? = null

        fun getInstance(context: Context): MuyDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        )
                            .also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, MuyDatabase::class.java, "pec_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}