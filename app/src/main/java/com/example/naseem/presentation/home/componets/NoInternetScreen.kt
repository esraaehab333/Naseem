package com.example.naseem.presentation.home.componets

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.naseem.R
import com.example.naseem.presentation.fav.components.EmptyStateScreen

@Composable
fun NoInternetScreen(color: Color) {
    EmptyStateScreen(
        icon = R.drawable.ic_alert,
        title = stringResource(R.string.no_internet_title),
        subtitle = stringResource(R.string.no_internet_home_message),
        color = color
    )
}