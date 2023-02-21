package eu.tutorials.countdowntimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.countdowntimer.ui.component.ReusableTextField
import eu.tutorials.countdowntimer.ui.component.StartAndStopButton
import eu.tutorials.countdowntimer.ui.rememberCountDownTimerState
import eu.tutorials.countdowntimer.ui.theme.CountdowntimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountdowntimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CountDownTimer()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CountDownTimer() {
    val appState = rememberCountDownTimerState()
    val secondsTextState = rememberSaveable { appState.seconds }
    val minutesTextState = rememberSaveable { appState.minutes }
    val hoursTextState = rememberSaveable { appState.hours }
    val isRunning = rememberSaveable { appState.isRunning }
    val enable = !isRunning.value

    //Start
    val emptySecondsField = secondsTextState.value == "00"
    val emptyMinutesField = minutesTextState.value == "00"
    val emptyHoursField = hoursTextState.value == "00"
    //End

    //Check if any of the input field is empty, then disable the timer button
    val enableButton = (secondsTextState.value != "00" && secondsTextState.value.isNotEmpty())
            || (minutesTextState.value != "00" && minutesTextState.value.isNotEmpty()) ||(hoursTextState.value != "00" && hoursTextState.value.isNotEmpty())

    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.maroon))
            .verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Timer",
            color = Color.White,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Box(
            Modifier
                .padding(40.dp)
                .size(300.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(
                        2.dp,
                        colorResource(id = R.color.pink)
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,

            ) {
            Card(elevation = 8.dp, modifier = Modifier.padding(8.dp)) {
                Row {
                    ReusableTextField(
                        text = hoursTextState.value,
                        onChange = { appState.modifyHours(it) },
                        enabled = enable,
                        modifier = Modifier.onFocusChanged {
                            /**
                             * Check if Seconds input field has focus or is empty,
                             * if true, set field to empty
                             */
                            if (it.hasFocus && emptyHoursField) {
                                hoursTextState.value = ""
                            }else if (!it.hasFocus && hoursTextState.value == ""){
                                hoursTextState.value = "00"
                            }
                        }
                    )
                    ReusableTextField(
                        text = minutesTextState.value,
                        onChange = { appState.modifyMinutes(it) },
                        enabled = enable,
                        modifier = Modifier.onFocusChanged {
                            /**
                             * Check if Seconds input field has focus or is empty,
                             * if true, set field to empty
                             */
                            if (it.hasFocus && emptyMinutesField) {
                                minutesTextState.value = ""
                            }else if (!it.hasFocus && minutesTextState.value == ""){
                                minutesTextState.value = "00"
                            }


                        }
                    )
                    ReusableTextField(
                        text = secondsTextState.value,
                        onChange = { appState.modifySeconds(it) },
                        enabled = enable,
                        modifier = Modifier.onFocusChanged {
                            /**
                             * Check if Seconds input field has focus or is empty,
                             * if true, set field to empty
                             */
                            if (it.hasFocus && emptySecondsField) {
                                secondsTextState.value = ""
                            }else if (!it.hasFocus && secondsTextState.value == ""){
                                secondsTextState.value = "00"
                            }
                        }
                    )
                }
            }
        }
        StartAndStopButton(
            isRunning = isRunning.value,
            onClick = {
                //Hide the soft-keyboard when running the timer
                keyboardController?.hide()
                //Check if timer not running, start timer else stop(cancel) timer
                if (!isRunning.value ) {
                    appState.startCountDown()
                } else {
                    appState.cancelTimer()
                }
            },
            isButtonDisabled = enableButton
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountdowntimerTheme {
        CountDownTimer()
    }
}