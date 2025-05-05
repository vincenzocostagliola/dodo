package dev.vincenzocostagliola.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [ActivityDTO::class], version = 1)
abstract class DodoDB : RoomDatabase() {
    abstract fun activitiesDao(): ActivitiesDao

    companion object {
        @Volatile
        private var INSTANCE: DodoDB? = null

        fun getDatabase(context: Context): DodoDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DodoDB::class.java,
                    "DodoDB"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}