package eu.tutorials.countdowntimer.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.countdowntimer.R

@Composable
fun ReusableTextField(modifier: Modifier = Modifier,
                      text: String, onChange: (String) -> Unit,
                      enabled: Boolean) {
    TextField(
        value = text,
        placeholder = {
            Text(
                text = "",
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color =colorResource(id = R.color.maroon),
                letterSpacing = 2.sp

            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { value ->
            onChange.invoke(value)
        },
        textStyle = TextStyle(
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.maroon),
            letterSpacing = 2.sp
        ),
        enabled = enabled,
        modifier = modifier
            .width(80.dp)
            .background(color = Color.Transparent)
    )
}