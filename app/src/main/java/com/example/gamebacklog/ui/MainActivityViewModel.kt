package com.example.gamebacklog.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gamebacklog.database.GameLogRepository
import com.example.gamebacklog.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val backLogRepository =
        GameLogRepository(application.applicationContext)

    val backlog: LiveData<List<Game>> = backLogRepository.getAllGames()

    fun insertGame(game: Game) {
        ioScope.launch {
            backLogRepository.insertGame(game)
        }
    }

    fun deleteGame(game: Game) {
        ioScope.launch {
            backLogRepository.deletegame(game)
        }
    }

    fun clearBacklog(){
        ioScope.launch {
            backLogRepository.clearBacklog()
        }
    }
}