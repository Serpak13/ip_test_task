package com.serpak.ip_test_task.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromTagsJson(tagsJson: String?): List<String>? {
        if (tagsJson.isNullOrEmpty()) return null
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(tagsJson, type)
    }

    @TypeConverter
    fun toTagsJson(tags: List<String>?): String? {
        return gson.toJson(tags)
    }
}