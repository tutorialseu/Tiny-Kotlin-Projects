package eu.tutorials.menusandnav.data

import androidx.annotation.DrawableRes
import eu.tutorials.menusandnav.R

data class Lib(@DrawableRes val icon: Int, val name: String)

val libraries = listOf<Lib>(Lib(R.drawable.ic_playlist_green, "Playlist"),
Lib(R.drawable.ic_microphone,"Artists"),
Lib(R.drawable.ic_baseline_album_24,"Album"), Lib(
        R.drawable.ic_baseline_music_note_24,"Songs"
),Lib(R.drawable.ic_genre,"Genre"))