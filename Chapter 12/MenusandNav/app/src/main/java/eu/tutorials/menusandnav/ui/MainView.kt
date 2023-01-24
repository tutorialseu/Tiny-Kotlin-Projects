package eu.tutorials.menusandnav.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import eu.tutorials.menusandnav.data.*
import eu.tutorials.menusandnav.ui.screens.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(

) {
    val controller: NavController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp
    val modifier = if (isSheetFullScreen)
        Modifier
            .fillMaxSize()
    else
        Modifier.fillMaxWidth()
    Modifier.fillMaxWidth()
    val currentScreen = remember {
        viewModel.currentScreen.value
    }
    val title = remember {
        mutableStateOf(currentScreen.title)
    }

    val dialogOpen = remember {
        mutableStateOf(false)
    }
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomBar: @Composable () -> Unit = {
        if (currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreens.Home) {
            BottomNavigation(Modifier.wrapContentSize()) {
                screensInBottom.forEach { item ->

                    BottomNavigationItem(selected = currentRoute == item.bRoute, onClick = {
                        controller.navigate(item.bRoute)
                        title.value = item.bTitle
                    },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.bTitle
                            )
                        }, label = {
                            Text(text = item.bTitle)
                        }, selectedContentColor = Color.White, unselectedContentColor = Color.Black)
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(
            topStart = roundedCornerRadius,
            topEnd = roundedCornerRadius
        ),
        sheetContent = {
            MoreBottomSheet(modifier = modifier)
        }) {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(title.value)
            }, navigationIcon = {
                IconButton(
                    onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                ) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Menu")
                }
            }, actions = {
                IconButton(onClick = {
                    scope.launch {
                        if (modalSheetState.isVisible)
                            modalSheetState.hide()
                        else
                            modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")

                }
            })
        }, scaffoldState = scaffoldState,
            drawerContent = {
                LazyColumn(Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)) {
                    items(screensInDrawer) { item ->
                        DrawerItem(item = item, selected = currentRoute == item.dRoute ) {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                            if (item.dRoute == "add_account") {

                                dialogOpen.value = true
                            } else {
                                controller.navigate(item.dRoute)
                                title.value = item.dTitle
                            }
                        }
                    }
                }
            }, bottomBar = {
                bottomBar()
            },
        ) { innerPadding ->

            Navigation(
                navController = controller,
                viewModel = viewModel, pd = innerPadding
            )

            AccountDialog(dialogOpen = dialogOpen)
        }
    }
}

@Composable
fun DrawerItem(
    selected:Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit
) {
    val background = if (selected) Color.DarkGray else Color.White
    Row(
        Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp).background(background)
            .clickable {
                onDrawerItemClicked()
            }) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dTitle,
            Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(
            text = item.dTitle,
            style = MaterialTheme.typography.h5,
        )
    }

}


@Composable
fun Navigation(
    navController: NavController,
    viewModel: MainViewModel, pd: PaddingValues
) {
    NavHost(
        navController = navController as NavHostController, startDestination =
        Screen.BottomScreens.Home.bRoute, modifier = Modifier.padding(pd)
    ) {
        composable(Screen.BottomScreens.Home.bRoute) {
            Home()
        }
        composable(Screen.BottomScreens.Browse.bRoute) {
            Browse()
        }

        composable(Screen.BottomScreens.Library.bRoute) {
            Library()
        }
        composable(Screen.DrawerScreen.Account.dRoute) {
            AccountView()
        }
        composable(Screen.DrawerScreen.Subscription.dRoute) {
            Subscription()
        }
    }
}