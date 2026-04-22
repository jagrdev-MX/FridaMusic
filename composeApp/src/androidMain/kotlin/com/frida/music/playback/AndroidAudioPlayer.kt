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

class AndroidAudioPlayer(private val context: Context) : AudioPlayer {
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
        controller?.let { player ->
            val mediaItem = MediaItem.fromUri(track.uri)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        }
    }

    override fun pause() {
        controller?.pause()
    }

    override fun resume() {
        controller?.play()
    }

    override fun stop() {
        controller?.stop()
    }
}
