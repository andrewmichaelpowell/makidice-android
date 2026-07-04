package xyz.andrewmichaelpowell.makidice

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.andrewmichaelpowell.makidice.ui.theme.Orange
import xyz.andrewmichaelpowell.makidice.ui.theme.Teal
import kotlin.random.Random

private val LabelRowGap = 0.dp
private val GroupSpacing = 32.dp

@Composable
fun D10View(onBack: () -> Unit) {
    var diceString by remember { mutableStateOf("0") }
    var diceValue by remember { mutableIntStateOf(0) }
    var difficultyString by remember { mutableStateOf("0") }
    var difficultyValue by remember { mutableIntStateOf(0) }
    var selected by remember { mutableIntStateOf(1) }
    var successesString by remember { mutableStateOf("0") }
    var successesValue by remember { mutableIntStateOf(0) }

    fun clear() {
        diceValue = 0
        difficultyValue = 0
        successesValue = 0
        diceString = "0"
        difficultyString = "0"
        successesString = "0"
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
            successesString = successesValue.toString()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.dice),
                    style = MaterialTheme.typography.displaySmall,
                    color = if (selected == 1) Teal else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
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
            Spacer(modifier = Modifier.height(LabelRowGap))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.difficulty),
                    style = MaterialTheme.typography.displaySmall,
                    color = if (selected == 2) Teal else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
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
            Spacer(modifier = Modifier.height(LabelRowGap))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.successes),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
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

            Spacer(modifier = Modifier.height(GroupSpacing))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DiceButton(
                    label = stringResource(R.string.dice),
                    tint = if (selected == 1) Teal else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (selected == 1) Color.White else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                ) { selected = 1 }
                DiceButton(
                    label = stringResource(R.string.difficulty),
                    tint = if (selected == 2) Teal else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (selected == 2) Color.White else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                ) { selected = 2 }
            }

            Spacer(modifier = Modifier.height(GroupSpacing))

            Column {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PoolNumberButton(1, Modifier.weight(1f)) { addValueToSide(1) }
                    PoolNumberButton(2, Modifier.weight(1f)) { addValueToSide(2) }
                    PoolNumberButton(3, Modifier.weight(1f)) { addValueToSide(3) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    PoolNumberButton(4, Modifier.weight(1f)) { addValueToSide(4) }
                    PoolNumberButton(5, Modifier.weight(1f)) { addValueToSide(5) }
                    PoolNumberButton(6, Modifier.weight(1f)) { addValueToSide(6) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    PoolNumberButton(7, Modifier.weight(1f)) { addValueToSide(7) }
                    PoolNumberButton(8, Modifier.weight(1f)) { addValueToSide(8) }
                    PoolNumberButton(9, Modifier.weight(1f)) { addValueToSide(9) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    DiceButton(label = stringResource(R.string.clear), tint = Orange, modifier = Modifier.weight(1f)) { clear() }
                    PoolNumberButton(10, Modifier.weight(1f)) { addValueToSide(10) }
                    DiceButton(label = stringResource(R.string.roll), tint = Orange, modifier = Modifier.weight(1f)) { roll() }
                }
            }

            Spacer(modifier = Modifier.height(GroupSpacing))
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(48.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "\u2039",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun PoolNumberButton(digit: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    DiceButton(
        label = digit.toString(),
        tint = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier,
        onClick = onClick,
    )
}
