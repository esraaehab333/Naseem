package com.example.naseem.presentation.settings.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@Composable
fun LanguageSection(
    color: Color,
    modifier: Modifier = Modifier,
    onLanguageChange: (String) -> Unit
) {
    val context = LocalContext.current
    var isArabic by remember {
        mutableStateOf(
            context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                .getString("language", "en") == "ar"
        )
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.language),
            style = TextStyle(
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color.Gray,
                letterSpacing = 1.sp
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            SettingsRow(
                color = color,
                icon = R.drawable.ic_language,
                title = if (isArabic) stringResource(R.string.arabic) else stringResource(R.string.english_us),
                trailingContent = {
                    Switch(
                        checked = isArabic,
                        onCheckedChange = { checked ->
                            isArabic = checked
                            onLanguageChange(if (checked) "ar" else "en")
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = White100,
                            checkedTrackColor = color,
                        )
                    )
                }
            )
        }
    }
}