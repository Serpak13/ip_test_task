package com.serpak.ip_test_task.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.serpak.ip_test_task.dao.ItemDao
import com.serpak.ip_test_task.entity.ItemEntity
import com.serpak.ip_test_task.utils.Converters

@Database(
    entities = [ItemEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val itemDao:ItemDao

}