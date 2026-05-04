package com.example.naseem.presentation.nav

import com.example.naseem.R

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = R.string.home,
            icon = R.drawable.ic_home,
            route = "home"
        ),
        BottomNavItem(
            label = R.string.alerts ,
            icon = R.drawable.ic_alert,
            route = "alert"
        ),
        BottomNavItem(
            label = R.string.favorite,
            icon = R.drawable.ic_fav,
            route = "favorite"
        )
        ,
        BottomNavItem(
            label = R.string.settings,
            icon = R.drawable.ic_settings,
            route = "settings"
        )
    )
}