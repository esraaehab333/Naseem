package com.example.naseem.presentation.settings.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.Gray100_2
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@Composable
fun LocationSection(color: Color){

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

                    var isChecked by remember { mutableStateOf(true) }

                    Switch(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = White100,
                            checkedTrackColor = color
                        )
                    )
                }
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 0.5.dp,
                color = color.copy(alpha = 0.3f)
            )

            SettingsRow(
                color = color,
                icon = R.drawable.ic_map,
                title = stringResource(R.string.choose_from_map),
                trailingContent = {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow),
                        contentDescription = null,
                        tint = Gray100_2
                    )
                },
                onClick = { /* Navigate to map */ }
            )
        }
    }
}