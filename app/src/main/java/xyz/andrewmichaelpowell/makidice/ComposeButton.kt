//  Maki Dice
//  github.com/andrewmichaelpowell

package xyz.andrewmichaelpowell.makidice

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ComposeButton(
    modifier: Modifier = Modifier,
    label: String,
    tint: Color,
    contentColor: Color = Color.White,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        contentPadding = PaddingValues(horizontal = 6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = tint,
            contentColor = contentColor,
        ),
    ) {
        ShrinkToFitText(
            text = label,
            maxFontSizeSp = MaterialTheme.typography.titleLarge.fontSize.value,
        )
    }
}

@Composable
fun ShrinkToFitText(
    text: String,
    maxFontSizeSp: Float,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textAlign: TextAlign? = null,
    minScaleFactor: Float = 0.2f,
) {
    val minFontSizeSp = maxFontSizeSp * minScaleFactor
    var fontSizeSp by remember(text, maxFontSizeSp) { mutableStateOf(maxFontSizeSp) }
    var readyToDraw by remember(text, maxFontSizeSp) { mutableStateOf(false) }

    Text(
        text = text,
        fontSize = fontSizeSp.sp,
        color = color,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        textAlign = textAlign,
        modifier = modifier.drawWithContent { if (readyToDraw) drawContent() },
        onTextLayout = { result ->
            if (result.didOverflowWidth && fontSizeSp > minFontSizeSp) {
                fontSizeSp = (fontSizeSp * 0.9f).coerceAtLeast(minFontSizeSp)
            } else {
                readyToDraw = true
            }
        },
    )
}