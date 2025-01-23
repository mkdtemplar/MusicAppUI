package com.example.musicappui

import androidx.annotation.DrawableRes

data class Dummy(
    @DrawableRes val icon : Int,
    val name : String
)

val libraries = listOf<Dummy>(
    Dummy(R.drawable.ic_playlist_green, "Playlist"),
    Dummy(R.drawable.ic_microphone,"Artists"),
    Dummy(R.drawable.ic_baseline_album_24,"Album"),
    Dummy(R.drawable.ic_baseline_music_note_24,"Songs"),
    Dummy(R.drawable.ic_genre,"Genre")

)
