package com.example.gamebacklog.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.gamebacklog.model.Game

class GameLogRepository (context: Context){

    private var gameLogDao: GameLogDao

    init {
        val gameLogRoomDatabase =
            GameLogRoomDatabase.getDatabase(context)
        gameLogDao = gameLogRoomDatabase!!.backlogDao()
    }

    suspend fun insertGame(game: Game){
        gameLogDao.insertGame(game)
    }

    suspend fun deletegame(game: Game){
        gameLogDao.deleteGame(game)
    }

    fun getAllGames(): LiveData<List<Game>>{
        return gameLogDao.getAllGames()
    }

    suspend fun updategame(game: Game){
        gameLogDao.updateGame(game)
    }

    suspend fun clearBacklog(){
        gameLogDao.clearBacklog()
    }
}