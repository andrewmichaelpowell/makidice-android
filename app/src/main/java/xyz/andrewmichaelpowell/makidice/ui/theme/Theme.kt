//  Maki Dice (Android)
//  github.com/andrewmichaelpowell

package xyz.andrewmichaelpowell.makidice.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Green = Color(0xFF045030)
val Orange = Color(0xFFF05C22)
val SecondarySystemBackgroundLight = Color(0xFFEFEFF4)
val SecondarySystemBackgroundDark = Color(0xFF1C1C1E)
val LabelLight = Color(0xFF000000)
val LabelDark = Color(0xFFFFFFFF)

private val LightColors = lightColorScheme(
    primary = Green,
    secondary = Orange,
    surfaceVariant = SecondarySystemBackgroundLight,
    onSurface = LabelLight,
)

private val DarkColors = darkColorScheme(
    primary = Green,
    secondary = Orange,
    surfaceVariant = SecondarySystemBackgroundDark,
    onSurface = LabelDark,
)

@Composable
fun MakiDiceTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
