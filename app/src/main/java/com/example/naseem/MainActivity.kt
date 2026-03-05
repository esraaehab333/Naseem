package com.example.naseem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.naseem.presentation.home.componets.BottomNavigationBar
import com.example.naseem.nav.NavHostContainer
import com.example.naseem.ui.theme.MildPrimary
import com.example.naseem.ui.theme.NaseemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NaseemTheme(dynamicColor = false, darkTheme = false) {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    bottomBar = {
                        BottomNavigationBar(navController = navController, color = MildPrimary)
                    }
                ) { padding ->
                    NavHostContainer(navController = navController, padding = padding , color = MildPrimary)
                }
            }
        }
    }
}
