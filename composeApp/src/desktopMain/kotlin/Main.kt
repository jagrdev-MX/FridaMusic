import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.frida.music.App

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "FridaMusic") {
        App(null) // Desktop scanner se implementará después
    }
}
