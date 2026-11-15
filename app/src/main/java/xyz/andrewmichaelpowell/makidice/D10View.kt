//  Maki Dice (Android)
//  github.com/andrewmichaelpowell

package xyz.andrewmichaelpowell.makidice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun D10View() {
    var diceString by rememberSaveable { mutableStateOf("") }
    var diceValue by rememberSaveable { mutableIntStateOf(0) }
    var difficultyString by rememberSaveable { mutableStateOf("") }
    var difficultyValue by rememberSaveable { mutableIntStateOf(0) }
    var selected by rememberSaveable { mutableIntStateOf(1) }
    var successesString by rememberSaveable { mutableStateOf("") }
    var successesValue by rememberSaveable { mutableIntStateOf(0) }
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

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth()) {
            ShrinkToFitText(
                text = stringResource(R.string.dice),
                maxFontSizeSp = MaterialTheme.typography.displaySmall.fontSize.value,
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

        Spacer(modifier = Modifier.height(16.dp))

        SegmentChooser(
            selectedIndex = selected - 1,
            labels = listOf(stringResource(R.string.dice), stringResource(R.string.difficulty)),
            onSelect = { selected = it + 1 },
        )

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
                ComposeButton(label = stringResource(R.string.clear), prominent = true, modifier = Modifier.weight(1f)) { clear() }
                D10Button(10, Modifier.weight(1f)) { addValueToSide(10) }
                ComposeButton(label = stringResource(R.string.roll), prominent = true, modifier = Modifier.weight(1f)) { roll() }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun D10Button(digit: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    ComposeButton(
        label = digit.toString(),
        modifier = modifier,
        onClick = onClick,
    )
}
