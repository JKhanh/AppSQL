package com.ptit.appsql.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ptit.appsql.order.Order
import com.ptit.appsql.student.Student

@Database(
    entities = [Student::class, Order::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun orderDao(): OrderDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun buildDatabase(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Student.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}