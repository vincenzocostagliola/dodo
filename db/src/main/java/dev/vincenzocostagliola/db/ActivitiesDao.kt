package dev.vincenzocostagliola.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActivitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoDb)

    @Delete
    suspend fun deleteActivity(todo: TodoDb)

    @Query("SELECT * FROM activities")
    suspend fun getAllActivities(): List<TodoDb>

    @Query("SELECT * FROM activities WHERE id = :id")
    suspend fun getComic(id: Int): TodoDb?

    @Query("SELECT * FROM activities WHERE status = :status")
    suspend fun getActivitiesByStatus(status: String): List<TodoDb>

    @Query("SELECT * FROM activities WHERE addedDate = :date")
    suspend fun getActivitiesByDate(date: String): List<TodoDb>

}