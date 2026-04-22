package com.frida.music.domain

data class AudioTrack(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val uri: String,
    val coverArtUri: String? = null
)
