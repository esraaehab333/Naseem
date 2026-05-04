package com.example.naseem.presentation.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.presentation.settings.viewModel.SettingsViewModel
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.Gray100_2
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@Composable
fun LocationSection(
    color: Color,
    settingsViewModel: SettingsViewModel,
    onNavigateToMap: () -> Unit
) {
    val useGps by settingsViewModel.useGps.collectAsState()
    val savedLabel by settingsViewModel.savedLocationLabel.collectAsState()
    Text(
        text = stringResource(R.string.location),
        fontFamily = PlusJakartaSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = Gray100,
        fontSize = 12.sp,
        letterSpacing = 1.sp
    )
    Spacer(modifier = Modifier.height(12.dp))
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = White100,
        shadowElevation = 1.1.dp,
    ) {
        Column {
            SettingsRow(
                color = color,
                icon = R.drawable.ic_gps,
                title = stringResource(R.string.use_gps),
                trailingContent = {
                    Switch(
                        checked = useGps,
                        onCheckedChange = { settingsViewModel.setUseGps(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = White100,
                            checkedTrackColor = color
                        )
                    )
                }
            )
            AnimatedVisibility(
                visible = !useGps,
                enter = fadeIn(),
                exit  = fadeOut()
            ) {
                Column {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = color.copy(alpha = 0.3f)
                    )

                    SettingsRow(
                        color = color,
                        icon  = R.drawable.ic_map,
                        title = if (savedLabel != null)
                            "${stringResource(R.string.choose_from_map)}\n$savedLabel"
                        else
                            stringResource(R.string.choose_from_map),
                        trailingContent = {},
                        onClick = onNavigateToMap
                    )
                }
            }
        }
    }
}