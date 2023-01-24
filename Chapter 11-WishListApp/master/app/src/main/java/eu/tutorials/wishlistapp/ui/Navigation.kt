package eu.tutorials.wishlistapp.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(viewModel:
               WishViewModel = viewModel(),
                navController: NavHostController
                = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeView(navController,viewModel)
        }
        composable(route = Screen.AddScreen.route +"/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )) { entry->
            val id  = if(entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            AddEditDetailView(id,viewModel,navController)
        }
    }
}