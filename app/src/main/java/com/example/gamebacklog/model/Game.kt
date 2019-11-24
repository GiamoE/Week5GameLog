package com.example.gamebacklog.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.sql.Date

@Parcelize
@Entity(tableName = "gameLogTable")
data class Game (

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "level")
    var platform: Int,

    @ColumnInfo(name = "releaseDay")
    var releaseDay: String,

    @ColumnInfo(name = "releaseMonth")
    var releaseMonth: String,

    @ColumnInfo(name = "releaseYear")
    var releaseYear: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id  : Long? = null

) : Parcelable