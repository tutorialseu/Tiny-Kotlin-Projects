package eu.tutorials.menusandnav.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.tutorials.menusandnav.data.Lib
import eu.tutorials.menusandnav.data.libraries

@Composable
fun Library() {
 LazyColumn(){
     items(libraries){lib->
         LibItem(lib = lib)
     }
 }
}


@Composable
fun LibItem(lib: Lib) {
    Column {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Icon(painter = painterResource(id = lib.icon), modifier =
                Modifier.padding(horizontal = 8.dp), contentDescription = lib.name)
            Text(text = lib.name)
        }
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Arrow Right")

    }
        Divider(color = Color.LightGray)
    }

}