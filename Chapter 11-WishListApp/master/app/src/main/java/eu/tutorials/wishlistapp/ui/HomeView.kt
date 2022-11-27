package eu.tutorials.wishlistapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tutorials.wishlistapp.R
import eu.tutorials.wishlistapp.data.DummyWish
import eu.tutorials.wishlistapp.data.Wish
import eu.tutorials.wishlistapp.ui.components.AppBarView

@Composable
fun HomeView(navController: NavController) {
    Scaffold(topBar = { AppBarView(title = stringResource(id = R.string.app_name))},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                onClick = {
                    navController.navigate(Screen.AddScreen.route)
                },
                contentColor = colorResource(id = R.color.white),
                backgroundColor = colorResource(id = R.color.black),

                ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(DummyWish.wishList) { wish->
                WishItem(wish) {
                    navController.navigate(Screen.AddScreen.route)
                } } } }
}

@Composable
fun WishItem(wish: Wish,onClick:()->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .clickable { onClick()},
        elevation = 10.dp,
        backgroundColor = Color.White
    ){
        Column(modifier = Modifier
            .padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}

