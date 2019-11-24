package com.example.gamebacklog.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gamebacklog.model.Game

@Database(entities = [Game::class], version = 2, exportSchema = false)
abstract class GameLogRoomDatabase : RoomDatabase(){

    abstract fun backlogDao(): GameLogDao

    companion object {
        private const val DATABASE_NAME = "BACKLOG_DATABASE"

        @Volatile
        private var gameLogRoomDatabaseInstance: GameLogRoomDatabase? = null

        fun getDatabase(context: Context): GameLogRoomDatabase? {
            if (gameLogRoomDatabaseInstance == null) {
                synchronized(GameLogRoomDatabase::class.java) {
                    if (gameLogRoomDatabaseInstance == null) {
                        gameLogRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            GameLogRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .build()
                    }
                }
            }
            return gameLogRoomDatabaseInstance
        }
    }
}