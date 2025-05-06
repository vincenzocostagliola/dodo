package dev.vincenzocostagliola.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActivitiesDao {

    @Insert
    suspend fun insertActivity(activity: ActivityDb)

    @Delete
    suspend fun deleteActivity(activity: ActivityDb)

    @Query("SELECT * FROM activities")
    suspend fun getAllActivities(): List<ActivityDb>

    @Query("SELECT * FROM activities WHERE id = :id")
    suspend fun getComic(id: Int): ActivityDb?

    @Query("SELECT * FROM activities WHERE status = :status")
    suspend fun getActivitiesByStatus(status: String): List<ActivityDb>

    @Query("SELECT * FROM activities WHERE addedDate = :date")
    suspend fun getActivitiesByDate(date: String): List<ActivityDb>

}