//  Maki Dice
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.andrewmichaelpowell.makidice.ui.theme.Orange
import xyz.andrewmichaelpowell.makidice.ui.theme.Teal

@Composable
fun MainView(onOpenD10: () -> Unit) {
    var diceNumber by remember { mutableStateOf("") }
    var diceType by remember { mutableStateOf("") }
    var editSide by remember { mutableIntStateOf(1) }
    var resetInput by remember { mutableIntStateOf(1) }
    var resultString by remember { mutableStateOf("") }
    var resultValue by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()

    fun revealAfterDelay(value: String) {
        resultString = ""
        scope.launch {
            delay(100.milliseconds)
            resultString = value
        }
    }


    fun quickRoll(quickDiceType: Int) {
        resultValue = Random.nextInt(1, quickDiceType + 1)
        editSide = 1
        resetInput = 1
        diceNumber = "1"
        diceType = quickDiceType.toString()
        revealAfterDelay(resultValue.toString())
    }

    fun clear() {
        editSide = 1
        diceNumber = ""
        diceType = ""
        resultString = ""
    }

    fun setRight(buttonValue: Int) {
        if (diceType.length < 3) {
            diceType += buttonValue.toString()
            resultString = "${diceNumber}d$diceType"
        }
    }

    fun setLeft(buttonValue: Int) {
        if (resetInput == 1) {
            diceNumber = ""
            diceType = ""
            resetInput = 0
        }
        if (diceNumber.length < 3) {
            diceNumber += buttonValue.toString()
            resultString = diceNumber
        }
    }

    fun appendDigit(buttonValue: Int) {
        if (editSide == 1) setLeft(buttonValue)
        if (editSide == 2) setRight(buttonValue)
    }

    fun zero() {
        if (editSide == 1 && diceNumber != "" && resetInput == 0) {
            appendDigit(0)
        }
        if (editSide == 2 && diceType != "" && resetInput == 0) {
            appendDigit(0)
        }
    }

    fun pressD() {
        if (editSide == 1 && resetInput == 0) {
            editSide = 2
            resultString = "${diceNumber}d"
        }
    }

    fun roll() {
        if (diceNumber != "" && diceType != "") {
            val n = diceNumber.toInt()
            val sides = diceType.toInt()
            var total = 0
            repeat(n) { total += Random.nextInt(1, sides + 1) }
            resultValue = total
            revealAfterDelay(resultValue.toString())
            editSide = 1
            resetInput = 1
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = resultString,
            style = MaterialTheme.typography.displaySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.End,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(top = 16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                QuickButton(4, Modifier.weight(1f)) { quickRoll(4) }
                QuickButton(6, Modifier.weight(1f)) { quickRoll(6) }
                QuickButton(8, Modifier.weight(1f)) { quickRoll(8) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                QuickButton(10, Modifier.weight(1f)) { quickRoll(10) }
                QuickButton(12, Modifier.weight(1f)) { quickRoll(12) }
                QuickButton(20, Modifier.weight(1f)) { quickRoll(20) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                ComposeButton(label = stringResource(R.string.clear), tint = Orange, modifier = Modifier.weight(1f)) { clear() }
                QuickButton(100, Modifier.weight(1f)) { quickRoll(100) }
                ComposeButton(label = stringResource(R.string.d10), tint = Teal, modifier = Modifier.weight(1f)) { onOpenD10() }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberButton(1, Modifier.weight(1f)) { appendDigit(1) }
                NumberButton(2, Modifier.weight(1f)) { appendDigit(2) }
                NumberButton(3, Modifier.weight(1f)) { appendDigit(3) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                NumberButton(4, Modifier.weight(1f)) { appendDigit(4) }
                NumberButton(5, Modifier.weight(1f)) { appendDigit(5) }
                NumberButton(6, Modifier.weight(1f)) { appendDigit(6) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                NumberButton(7, Modifier.weight(1f)) { appendDigit(7) }
                NumberButton(8, Modifier.weight(1f)) { appendDigit(8) }
                NumberButton(9, Modifier.weight(1f)) { appendDigit(9) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                ComposeButton(label = stringResource(R.string.d_button), tint = Orange, modifier = Modifier.weight(1f)) { pressD() }
                ComposeButton(label = "0", tint = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f)) { zero() }
                ComposeButton(label = stringResource(R.string.roll), tint = Orange, modifier = Modifier.weight(1f)) { roll() }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun QuickButton(sides: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    ComposeButton(
        label = "1d$sides",
        tint = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier,
        onClick = onClick,
    )
}

@Composable
private fun NumberButton(digit: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    ComposeButton(
        label = digit.toString(),
        tint = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier,
        onClick = onClick,
    )
}

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
        colors = ButtonDefaults.buttonColors(
            containerColor = tint,
            contentColor = contentColor,
        ),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Clip,
        )
    }
}