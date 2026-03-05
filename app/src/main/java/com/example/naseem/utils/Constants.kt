package com.example.naseem.utils

import com.example.naseem.presentation.home.componets.BottomNavItem
import com.example.naseem.R

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            icon = R.drawable.ic_home,
            route = "home"
        ),
        BottomNavItem(
            label = "Explore",
            icon = R.drawable.ic_explore,
            route = "explore"
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