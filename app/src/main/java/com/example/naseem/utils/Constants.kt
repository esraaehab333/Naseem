package com.example.naseem.utils

import com.example.naseem.R

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            icon = R.drawable.ic_home,
            route = "home"
        ),
        BottomNavItem(
            label = "Alerts",
            icon = R.drawable.ic_alert,
            route = "alert"
        ),
        BottomNavItem(
            label = "Favorite",
            icon = R.drawable.ic_fav,
            route = "favorite"
        )
        ,
        BottomNavItem(
            label = "Settings",
            icon = R.drawable.ic_settings,
            route = "settings"
        )
    )
}