package eu.tutorials.menusandnav.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.tutorials.menusandnav.data.Screen

class MainViewModel:ViewModel() {

    private val _currentScreen:MutableState<Screen> =
        mutableStateOf(Screen.BottomScreens.Home)

    val currentScreen:MutableState<Screen>
    get() = _currentScreen

    fun setCurrentScreen(screen: Screen){
        _currentScreen.value = screen
    }
}