package com.test.testmovies.data.storage.model.sorting

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sorting")
data class Sorting(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val parameter: String?
    )