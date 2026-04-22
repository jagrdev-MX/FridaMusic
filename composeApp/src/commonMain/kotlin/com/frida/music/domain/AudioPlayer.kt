package com.frida.music.domain

interface AudioPlayer {
    fun play(track: AudioTrack)
    fun pause()
    fun resume()
    fun stop()
}
