package com.example.punktozercy.model

import androidx.room.Query
import androidx.room.Transaction

interface ShoppingHistoryDao {

    @Transaction
    @Query("SELECT * FROM Playlist")
    fun getPlaylistsWithSongs(): List<UserWithProducts>
}