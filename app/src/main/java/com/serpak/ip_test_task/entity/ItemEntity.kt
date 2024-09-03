package com.serpak.ip_test_task.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "time") val time: Int,
    @ColumnInfo(name = "tags") val tags: List<String>,
    @ColumnInfo(name = "amount") val amount:Int
)