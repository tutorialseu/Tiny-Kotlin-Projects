package eu.tutorials.wishlistapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.tutorials.wishlistapp.R
import eu.tutorials.wishlistapp.data.Wish
import eu.tutorials.wishlistapp.ui.components.AppBarView


@Composable
fun AddEditDetailView(wish: Wish) {
    var title by remember {
        mutableStateOf(wish.title)
    }
    var desc by remember {
        mutableStateOf(wish.description)
    }
    var id by remember {
        mutableStateOf(wish.id)
    }
    Scaffold(topBar = {
        AppBarView(
            title = if (id != 0L) stringResource(id = R.string.update_wish) else stringResource(
                id = R.string.add_wish
            )
        )
    }) {
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
                value = title,
                onValueChanged = {
                    title = it
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = stringResource(id = R.string.description),
                value = desc,
                onValueChanged = {
                    desc = it
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
                    text = if (id != 0L) stringResource(id = R.string.update_wish) else stringResource(
                        id = R.string.add_wish
                    ),
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
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
                color = colorResource(id = R.color.black),
            )
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        )
    )
}

@Preview
@Composable
fun WishTestFieldPrev() {
    WishTextField(label = "text", value = "text", onValueChanged = {})
}