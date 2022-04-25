package com.example.ceibatest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ceibatest.data.model.responses.User

@Database(entities = [User::class], version = 9)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME : String = "ceiba_db"
    }
}