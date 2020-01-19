package com.codingwithtushar.omdbmoviesshows.databaserepository

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codingwithtushar.omdbmoviesshows.models.Search
import com.codingwithtushar.omdbmoviesshows.ui.MainApplication
import java.util.concurrent.locks.ReentrantLock

@Database(entities = [Search::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null
        private val lock = ReentrantLock()

        fun getDatabaseInstance(): MovieDatabase {
            if (instance == null) {
                lock.lock()
                try {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            MainApplication.getContext(),
                            MovieDatabase::class.java, "moviedatabase.db"
                        ).build()
                    }
                } finally {
                    lock.unlock()
                }
            }
            return instance as MovieDatabase
        }
    }
}