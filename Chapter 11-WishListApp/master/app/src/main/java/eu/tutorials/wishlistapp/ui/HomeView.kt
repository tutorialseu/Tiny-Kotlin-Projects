package eu.tutorials.wishlistapp.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.tutorials.wishlistapp.R
import eu.tutorials.wishlistapp.data.Wish
import eu.tutorials.wishlistapp.ui.components.AppBarView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(navController: NavController,
viewModel: WishViewModel) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(topBar = { AppBarView(title = stringResource(id = R.string.app_name)) },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                onClick = {
                    navController.navigate(Screen.AddScreen.route +"/0L")
                },
                contentColor = colorResource(id = R.color.white),
                backgroundColor = colorResource(id = R.color.black),

                ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },scaffoldState = scaffoldState,
        backgroundColor = colorResource(id = R.color.app_bar_color)) {
        val wishList = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(wishList.value) { wish->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if(it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart){
                                viewModel.deleteWish(wish)
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    "${wish.title} has been deleted"
                                )
                            }
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier
                        .padding(vertical = Dp(1f)),
                    directions = setOf(
                        DismissDirection.EndToStart
                    ),
                    dismissThresholds = { direction ->
                        FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                    },
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.Transparent
                                else -> Color.Red
                            }
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Delete

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = Dp(16f)),
                            contentAlignment = alignment
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Delete Icon",
                                    modifier = Modifier.scale(scale)
                                )
                            }

                        }
                    }
                ){
                WishItem(wish) {
                    val id  = wish.id
                    navController.navigate(Screen.AddScreen.route +"/$id")
                } }} } }
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
            .clickable { onClick() },
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

