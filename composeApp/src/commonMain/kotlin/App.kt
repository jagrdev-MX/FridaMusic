package com.frida.music

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.frida.music.domain.AudioPlayer
import com.frida.music.domain.AudioScanner
import ui.screens.*
import ui.theme.FridaMusicTheme

@Composable
fun App(audioScanner: AudioScanner? = null, audioPlayer: AudioPlayer? = null) {
    FridaMusicTheme {
        var currentScreen by remember { mutableStateOf("home") }
        
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentScreen == "home",
                        onClick = { currentScreen = "home" },
                        icon = { Text("🏠") },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == "player",
                        onClick = { currentScreen = "player" },
                        icon = { Text("🎵") },
                        label = { Text("Player") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == "library",
                        onClick = { currentScreen = "library" },
                        icon = { Text("📚") },
                        label = { Text("Library") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == "mixer",
                        onClick = { currentScreen = "mixer" },
                        icon = { Text("🎛️") },
                        label = { Text("Mixer") }
                    )
                    // TODO: Monetization - Add Premium/Ads status indicator here later
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (currentScreen) {
                    "home" -> HomeScreen(audioScanner, audioPlayer)
                    "player" -> PlayerScreen(audioPlayer)
                    "library" -> LibraryScreen()
                    "mixer" -> MixerScreen()
                }
            }
        }
    }
}
