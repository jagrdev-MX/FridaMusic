package com.frida.music.domain

import kotlinx.coroutines.flow.StateFlow

interface AudioPlayer {
    val currentTrack: StateFlow<AudioTrack?>
    val isPlaying: StateFlow<Boolean>

    fun play(track: AudioTrack)
    fun pause()
    fun resume()
    fun stop()

    // Sound Effects
    fun setEqualizerBand(band: Int, level: Float) // Level from 0.0 to 1.0 (mapped to dB in impl)
    fun setBassBoost(level: Float) // Level from 0.0 to 1.0
    fun setSurround(level: Float) // Level from 0.0 to 1.0
}
