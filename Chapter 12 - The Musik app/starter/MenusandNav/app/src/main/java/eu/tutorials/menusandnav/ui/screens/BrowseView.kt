package eu.tutorials.menusandnav.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.tutorials.menusandnav.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Browse() {
 val categories = listOf("Hits","Happy","Workout","Running","TGIF","Yoga")
    LazyVerticalGrid(cells = GridCells.Fixed(2) ){
        items(categories){cat->
            BrowserItem(cat = cat, drawable = R.drawable.ic_browse_green)
        }
    }
}

@Composable
fun BrowserItem(cat:String, drawable:Int) {
    Card(modifier = Modifier
        .padding(16.dp)
        .size(200.dp), border = BorderStroke(3.dp, color = Color.DarkGray)) {
        Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = cat)
            Image(painter = painterResource(id = drawable), contentDescription =cat)
        }
    }
}