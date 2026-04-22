package com.frida.music.domain

interface AudioScanner {
    suspend fun scanLocalAudio(): List<AudioTrack>
}
