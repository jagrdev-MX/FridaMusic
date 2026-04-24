package com.frida.music.playback

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.frida.music.domain.AudioPlayer
import com.frida.music.domain.AudioTrack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AndroidAudioPlayer(private val context: Context) : AudioPlayer {
    private val _currentTrack = MutableStateFlow<AudioTrack?>(null)
    override val currentTrack: StateFlow<AudioTrack?> = _currentTrack.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    override val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private var browserFuture: ListenableFuture<MediaController>? = null
    private val controller: MediaController?
        get() = if (browserFuture?.isDone == true) browserFuture?.get() else null

    init {
        val sessionToken = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        browserFuture = MediaController.Builder(context, sessionToken).buildAsync()
        browserFuture?.addListener({
            // Controller is ready
        }, MoreExecutors.directExecutor())
    }

    override fun play(track: AudioTrack) {
        _currentTrack.value = track
        _isPlaying.value = true
        controller?.let { player ->
            val mediaItem = MediaItem.fromUri(track.uri)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        }
    }

    override fun pause() {
        _isPlaying.value = false
        controller?.pause()
    }

    override fun resume() {
        _isPlaying.value = true
        controller?.play()
    }

    override fun stop() {
        _isPlaying.value = false
        controller?.stop()
    }

    override fun setEqualizerBand(band: Int, level: Float) {
        val extras = android.os.Bundle().apply {
            putInt("band", band)
            putFloat("level", level)
        }
        controller?.sendCustomCommand(androidx.media3.session.SessionCommand("SET_EQ_BAND", android.os.Bundle.EMPTY), extras)
    }

    override fun setBassBoost(level: Float) {
        val extras = android.os.Bundle().apply {
            putFloat("level", level)
        }
        controller?.sendCustomCommand(androidx.media3.session.SessionCommand("SET_BASS_BOOST", android.os.Bundle.EMPTY), extras)
    }

    override fun setSurround(level: Float) {
        val extras = android.os.Bundle().apply {
            putFloat("level", level)
        }
        controller?.sendCustomCommand(androidx.media3.session.SessionCommand("SET_SURROUND", android.os.Bundle.EMPTY), extras)
    }
}
