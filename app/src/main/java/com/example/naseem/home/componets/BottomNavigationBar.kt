package com.example.naseem.home.componets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.naseem.utils.Constants
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.White100


@Composable
fun BottomNavigationBar(navController: NavHostController, color: Color) {
    val activeColor: Color = color
    val inactiveColor: Color = Gray100
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp),
        color = White100
    ) {
        NavigationBar(
            containerColor = White100,
            tonalElevation = 0.dp,
            modifier = Modifier.height(85.dp).padding(horizontal = 25.dp)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            Constants.BottomNavItems.forEach { navItem ->
                val isSelected = currentRoute == navItem.route
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (currentRoute != navItem.route) {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(navItem.icon),
                            contentDescription = navItem.label,
                            modifier = Modifier.size(22.dp).padding(2.dp)
                        )
                    },
                    label = {
                        Text(
                            text = navItem.label,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = activeColor,
                        unselectedIconColor = inactiveColor,
                        selectedTextColor = activeColor,
                        unselectedTextColor = inactiveColor,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}