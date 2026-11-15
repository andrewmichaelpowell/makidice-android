//  Maki Dice (Android)
//  github.com/andrewmichaelpowell

package xyz.andrewmichaelpowell.makidice

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.size
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.andrewmichaelpowell.makidice.ui.theme.Orange

@Composable
fun frostedColor(): Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)

@Composable
fun frostedBorder(): BorderStroke =
    BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

@Composable
fun ComposeButton(
    modifier: Modifier = Modifier,
    label: String,
    prominent: Boolean = false,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = if (prominent) Orange else frostedColor(),
        contentColor = if (prominent) Color.White else MaterialTheme.colorScheme.onSurface,
        border = if (prominent) null else frostedBorder(),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp),
            contentAlignment = Alignment.Center,
        ) {
            ShrinkToFitText(
                text = label,
                maxFontSizeSp = MaterialTheme.typography.titleLarge.fontSize.value,
            )
        }
    }
}

/** A button like [ComposeButton] that shows an icon in place of a label. */
@Composable
fun ComposeIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    contentDescription: String,
    prominent: Boolean = false,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = if (prominent) Orange else frostedColor(),
        contentColor = if (prominent) Color.White else MaterialTheme.colorScheme.onSurface,
        border = if (prominent) null else frostedBorder(),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                painterResource(icon),
                contentDescription = contentDescription,
                modifier = Modifier.size(26.dp),
            )
        }
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
    var fontSizeSp by remember(maxFontSizeSp) { mutableFloatStateOf(maxFontSizeSp) }
    var readyToDraw by remember(maxFontSizeSp) { mutableStateOf(false) }

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