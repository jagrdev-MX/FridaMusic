package com.frida.music.presentation

import androidx.compose.runtime.*
import com.frida.music.domain.AudioPlayer
import com.frida.music.domain.AudioScanner
import com.frida.music.domain.AudioTrack
import kotlinx.coroutines.*

class MusicViewModel(
    private val player: AudioPlayer,
    private val scanner: AudioScanner
) {
    private val scope = CoroutineScope(Dispatchers.Main)

    var tracks = mutableStateListOf<AudioTrack>()
    var currentTrack by mutableStateOf<AudioTrack?>(null)
    var isPlaying by mutableStateOf(false)

    fun loadTracks() {
        scope.launch {
            tracks.clear()
            val result = scanner.scanLocalAudio() // ✅ suspend correctamente
            tracks.addAll(result)
        }
    }

    fun play(track: AudioTrack) {
        currentTrack = track
        player.play(track)
        isPlaying = true
    }

    fun pause() {
        player.pause()
        isPlaying = false
    }
}