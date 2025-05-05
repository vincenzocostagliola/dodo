package dev.vincenzocostagliola.db


import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromListStringToString(list : List<String>) : String {

        return "pippo"
    }

    @TypeConverter
    fun fromStringToList(savedString: String) : List<String>{
        return emptyList()
    }
}