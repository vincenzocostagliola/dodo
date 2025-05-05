package dev.vincenzocostagliola.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityDTO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("description")
    val description: String?,
    @ColumnInfo("addedDate")
    val addedDate: String,
    @ColumnInfo("status")
    val status: String
)
