package com.codingwithtushar.omdbmoviesshows.databaserepository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codingwithtushar.omdbmoviesshows.models.Search

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(search: Search)

    @Query("SELECT * FROM movietable")
    fun  getData(): LiveData<List<Search>>

    @Delete
    fun deleteData(search: Search)

}