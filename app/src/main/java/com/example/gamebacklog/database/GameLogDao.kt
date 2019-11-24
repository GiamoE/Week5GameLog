package com.example.gamebacklog.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gamebacklog.model.Game

@Dao
interface GameLogDao {

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Update
    suspend fun updateGame(game: Game)

    @Query("SELECT * FROM gameLogTable")
    fun getAllGames():LiveData<List<Game>>

    @Query("DELETE FROM gameLogTable")
    suspend fun clearBacklog()
}