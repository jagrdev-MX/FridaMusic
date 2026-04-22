package com.frida.music.domain

import kotlinx.coroutines.flow.StateFlow

interface AudioPlayer {
    val currentTrack: StateFlow<AudioTrack?>
    val isPlaying: StateFlow<Boolean>

    fun play(track: AudioTrack)
    fun pause()
    fun resume()
    fun stop()
}
