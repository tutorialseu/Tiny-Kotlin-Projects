package eu.tutorials.countdowntimer.ui.component


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.countdowntimer.R

@Composable
fun StartAndStopButton(
    isButtonDisabled: Boolean,
    isRunning: Boolean,
    onClick: () -> Unit
) {

        Button(
            onClick = {
                //Lambda function to handle button click
                onClick()
            },
            modifier =
            Modifier
                .height(60.dp)
                .width(200.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor =  if (isButtonDisabled) colorResource(id = R.color.white) else colorResource(id = R.color.pink),
                contentColor = colorResource(id = R.color.white),
            ),
            enabled = isButtonDisabled
        ) {
            /**
             * Checks timer state and changes the button accordingly.
             * If timer is idle show [Start], if timer is running show [Stop]
             */
            /**
             * Checks timer state and changes the button accordingly.
             * If timer is idle show [Start], if timer is running show [Stop]
             */
            /**
             * Checks timer state and changes the button accordingly.
             * If timer is idle show [Start], if timer is running show [Stop]
             */

            /**
             * Checks timer state and changes the button accordingly.
             * If timer is idle show [Start], if timer is running show [Stop]
             */
            val status = if (!isRunning) {
                "START"
            } else {
                "STOP"
            }

            Text(
                status,
                fontSize = 22.sp,
                color = if (isButtonDisabled) colorResource(id = R.color.pink) else colorResource(id = R.color.white),
                fontWeight = FontWeight.Bold
            )
        }
}
