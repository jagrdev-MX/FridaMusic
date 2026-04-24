package com.frida.music.playback

import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.media.audiofx.Virtualizer
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

class PlaybackService : MediaSessionService() {
    private var mediaSession: MediaSession? = null
    private var equalizer: Equalizer? = null
    private var bassBoost: BassBoost? = null
    private var virtualizer: Virtualizer? = null

    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        
        // Inicializar efectos usando el AudioSessionId de ExoPlayer
        val sessionId = player.audioSessionId
        try {
            equalizer = Equalizer(0, sessionId).apply { enabled = true }
            bassBoost = BassBoost(0, sessionId).apply { enabled = true }
            virtualizer = Virtualizer(0, sessionId).apply { enabled = true }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mediaSession = MediaSession.Builder(this, player)
            .setCallback(CustomMediaSessionCallback())
            .build()
    }

    private inner class CustomMediaSessionCallback : MediaSession.Callback {
        override fun onCustomCommand(
            session: MediaSession,
            controller: MediaSession.ControllerInfo,
            customCommand: SessionCommand,
            args: android.os.Bundle
        ): ListenableFuture<SessionResult> {
            when (customCommand.customAction) {
                "SET_EQ_BAND" -> {
                    val band = args.getInt("band")
                    val level = args.getFloat("level")
                    // Mapear 0.0-1.0 a el rango del ecualizador (usualmente -1500 a 1500 mB)
                    equalizer?.let {
                        val range = it.bandLevelRange
                        val min = range[0]
                        val max = range[1]
                        val newLevel = (min + (level * (max - min))).toInt()
                        it.setBandLevel(band.toShort(), newLevel.toShort())
                    }
                }
                "SET_BASS_BOOST" -> {
                    val level = args.getFloat("level")
                    bassBoost?.setStrength((level * 1000).toInt().toShort())
                }
                "SET_SURROUND" -> {
                    val level = args.getFloat("level")
                    virtualizer?.setStrength((level * 1000).toInt().toShort())
                }
            }
            return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        equalizer?.release()
        bassBoost?.release()
        virtualizer?.release()
        super.onDestroy()
    }
}
