package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF060E20)
val SurfaceLow = Color(0xFF091328)
val SurfaceHigh = Color(0xFF141F38)

val PrimaryViolet = Color(0xFF8B5CF6)
val PrimaryCyan = Color(0xFF06B6D4)
val GradientStart = Color(0xFFBA9EFF)
val GradientEnd = Color(0xFF53DDFC)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryViolet,
    secondary = PrimaryCyan,
    background = DarkBackground,
    surface = SurfaceLow,
    surfaceVariant = SurfaceHigh
)

@Composable
fun FridaMusicTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
