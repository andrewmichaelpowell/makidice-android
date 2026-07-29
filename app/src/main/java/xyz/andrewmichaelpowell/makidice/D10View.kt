//  Maki Dice
//  github.com/andrewmichaelpowell

package xyz.andrewmichaelpowell.makidice

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.andrewmichaelpowell.makidice.ui.theme.Orange
import xyz.andrewmichaelpowell.makidice.ui.theme.Teal

@Composable
fun D10View(onBack: () -> Unit) {
    var diceString by remember { mutableStateOf("") }
    var diceValue by remember { mutableIntStateOf(0) }
    var difficultyString by remember { mutableStateOf("") }
    var difficultyValue by remember { mutableIntStateOf(0) }
    var selected by remember { mutableIntStateOf(1) }
    var successesString by remember { mutableStateOf("") }
    var successesValue by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()

    fun clear() {
        diceValue = 0
        difficultyValue = 0
        successesValue = 0
        diceString = ""
        difficultyString = ""
        successesString = ""
    }

    fun addValueToSide(buttonValue: Int) {
        if (selected == 1) {
            diceValue = buttonValue
            diceString = diceValue.toString()
        }
        if (selected == 2) {
            difficultyValue = buttonValue
            difficultyString = difficultyValue.toString()
        }
    }

    fun roll() {
        if (diceValue != 0 && difficultyValue != 0) {
            var successes = 0
            repeat(diceValue) {
                val r = Random.nextInt(1, 11)
                when {
                    r == 1 -> successes -= 1
                    r == 10 -> successes += 2
                    r >= difficultyValue -> successes += 1
                }
            }
            successesValue = successes
            successesString = ""
            scope.launch {
                delay(100.milliseconds)
                successesString = successesValue.toString()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth()) {
                ShrinkToFitText(
                    text = stringResource(R.string.dice),
                    maxFontSizeSp = MaterialTheme.typography.displaySmall.fontSize.value,
                    color = if (selected == 1) Teal else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = diceString,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End,
                )
            }
            Spacer(modifier = Modifier.height(0.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                ShrinkToFitText(
                    text = stringResource(R.string.difficulty),
                    maxFontSizeSp = MaterialTheme.typography.displaySmall.fontSize.value,
                    color = if (selected == 2) Teal else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = difficultyString,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End,
                )
            }
            Spacer(modifier = Modifier.height(0.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                ShrinkToFitText(
                    text = stringResource(R.string.successes),
                    maxFontSizeSp = MaterialTheme.typography.displaySmall.fontSize.value,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = successesString,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ComposeButton(
                    label = stringResource(R.string.dice),
                    tint = if (selected == 1) Teal else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (selected == 1) Color.White else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                ) { selected = 1 }
                ComposeButton(
                    label = stringResource(R.string.difficulty),
                    tint = if (selected == 2) Teal else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (selected == 2) Color.White else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                ) { selected = 2 }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    D10Button(1, Modifier.weight(1f)) { addValueToSide(1) }
                    D10Button(2, Modifier.weight(1f)) { addValueToSide(2) }
                    D10Button(3, Modifier.weight(1f)) { addValueToSide(3) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    D10Button(4, Modifier.weight(1f)) { addValueToSide(4) }
                    D10Button(5, Modifier.weight(1f)) { addValueToSide(5) }
                    D10Button(6, Modifier.weight(1f)) { addValueToSide(6) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    D10Button(7, Modifier.weight(1f)) { addValueToSide(7) }
                    D10Button(8, Modifier.weight(1f)) { addValueToSide(8) }
                    D10Button(9, Modifier.weight(1f)) { addValueToSide(9) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    ComposeButton(label = stringResource(R.string.clear), tint = Orange, modifier = Modifier.weight(1f)) { clear() }
                    D10Button(10, Modifier.weight(1f)) { addValueToSide(10) }
                    ComposeButton(label = stringResource(R.string.roll), tint = Orange, modifier = Modifier.weight(1f)) { roll() }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        val onSurfaceColor = MaterialTheme.colorScheme.onSurface
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(36.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.size(14.dp)) {
                val strokeWidth = 2.5.dp.toPx()
                val w = size.width
                val h = size.height
                val path = Path().apply {
                    moveTo(w * 0.68f, h * 0.12f)
                    lineTo(w * 0.28f, h * 0.5f)
                    lineTo(w * 0.68f, h * 0.88f)
                }
                drawPath(
                    path = path,
                    color = onSurfaceColor,
                    style = Stroke(
                        width = strokeWidth,
                        cap = androidx.compose.ui.graphics.StrokeCap.Round,
                        join = androidx.compose.ui.graphics.StrokeJoin.Round,
                    ),
                )
            }
        }
    }
}

@Composable
private fun D10Button(digit: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    ComposeButton(
        label = digit.toString(),
        tint = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier,
        onClick = onClick,
    )
}
