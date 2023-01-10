package com.test.testmovies.data.db.sorting

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.testmovies.data.storage.model.sorting.Sorting

@Dao
interface SortingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSorting(sorting: Sorting)

    @Delete
    suspend fun deleteSorting(sorting: Sorting)

    @Query("SELECT * FROM sorting")
    fun getAllSorting(): LiveData<List<Sorting>>

}