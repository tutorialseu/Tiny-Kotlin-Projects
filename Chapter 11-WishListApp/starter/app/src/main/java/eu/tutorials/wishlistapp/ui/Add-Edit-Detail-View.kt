package eu.tutorials.wishlistapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.wishlistapp.R


@Composable
fun AddEditDetailView() {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        WishTextField(
            label = stringResource(id = R.string.title),
            value = "",
            onValueChanged = {

            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        WishTextField(
            label = stringResource(id = R.string.description),
            value = "",
            onValueChanged = {

            }
        )

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {

            },
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .size(55.dp),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.white),
                contentColor = colorResource(id = R.color.black),
            )
        ) {
            Text(
                text =  stringResource(id = R.string.add_wish),
                style = TextStyle(
                    fontSize = 18.sp
                )
            )
        }
    }
}

@Preview
@Composable
fun AddEditDetailPrev() {
    AddEditDetailView()
}
@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = {
            Text(
                text = label,
                style = TextStyle(
                    color = Color.White
                )
            )
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = colorResource(id = R.color.white),
            focusedBorderColor = colorResource(id = R.color.white),
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.white),
            focusedLabelColor = colorResource(id = R.color.white),
            unfocusedLabelColor = colorResource(id = R.color.white),
        )
    )
}

@Preview
@Composable
fun WishTestFieldPrev() {
    WishTextField(label = "text", value = "text", onValueChanged = {})
}