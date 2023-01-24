package eu.tutorials.wishlistapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import eu.tutorials.wishlistapp.data.Wish

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeView(navController)
        }
        composable(route = Screen.AddScreen.route +"/{id}/{title}/{desc}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                },
                navArgument("title"){
                    type =NavType.StringType
                    defaultValue = ""
                    nullable = true
                },
                navArgument("desc"){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )) { entry->
            val id  = entry.arguments?.getLong("id")
            val title = entry.arguments?.getString("title")
            val desc = entry.arguments?.getString("desc")
            id?.let { Wish(id = it,title = title.toString(), description = desc.toString()) }
                ?.let { AddEditDetailView(it) }
        }
    }
}