package com.test.testmovies.data.db.sorting

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.testmovies.data.storage.model.sorting.Sorting

@Database(entities = [Sorting::class], version = 1)
abstract class SortingDatabase: RoomDatabase() {
    abstract fun sortingDao(): SortingDao

    companion object {
        @Volatile
        var INSTANCE: SortingDatabase ?= null

        @Synchronized
        fun getInstance(context: Context): SortingDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    SortingDatabase::class.java,
                    "sorting.db"
                ).fallbackToDestructiveMigration().build()
            }

            return INSTANCE as SortingDatabase
        }
    }
}
