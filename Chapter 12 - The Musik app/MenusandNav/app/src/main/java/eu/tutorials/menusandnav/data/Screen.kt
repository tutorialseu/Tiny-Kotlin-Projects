package eu.tutorials.menusandnav.data

import androidx.annotation.DrawableRes
import eu.tutorials.menusandnav.R

sealed class Screen(val title: String, val route: String) {

    sealed class BottomScreens(
        val bTitle: String, val bRoute: String,
        @DrawableRes val icon: Int
    ) : Screen(bTitle, bRoute) {
        object Home : BottomScreens(
            "Home", "home",
            R.drawable.ic_music_player_green
        )

        object Library : BottomScreens(
            "Library", "library",
            R.drawable.ic_baseline_video_library_24
        )

        object Browse : BottomScreens(
            "Browse", "browse",
            R.drawable.ic_browse_green
        )
    }


    sealed class DrawerScreen(val dTitle: String, val dRoute: String,
                              @DrawableRes val icon: Int) :
        Screen(dTitle, dRoute) {

        object Account : DrawerScreen(
            "Account",
            "account",
            R.drawable.ic_account
        )

        object Subscription : DrawerScreen(
            "Subscription",
            "subscribe",
            R.drawable.ic_subscribe
        )
        object AddAccount : DrawerScreen(
            "Add Account",
            "add_account",
            R.drawable.ic_baseline_person_add_alt_1_24
        )
    }
}

val screensInBottom = listOf(
    Screen.BottomScreens.Home,
    Screen.BottomScreens.Browse, Screen.BottomScreens.Library
)

val screensInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.AddAccount
)

