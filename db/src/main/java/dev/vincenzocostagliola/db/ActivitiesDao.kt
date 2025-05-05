package dev.vincenzocostagliola.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActivitiesDao {

    @Insert
    suspend fun insertActivity(activity: ActivityDTO)

    @Delete
    suspend fun deleteActivity(activity: ActivityDTO)

    @Query("SELECT * FROM comics")
    suspend fun getAllActivities(): List<ActivityDTO>

    @Query("SELECT * FROM comics WHERE id = :isbn")
    suspend fun getComic(isbn: String): Comic?

}