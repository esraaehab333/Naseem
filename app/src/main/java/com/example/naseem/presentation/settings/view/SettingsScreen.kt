package com.example.naseem.presentation.settings.view
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.NaseemTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.naseem.presentation.settings.components.LocationSection
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.HotPrimary
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100
import com.example.naseem.presentation.settings.components.TemperatureAndWindSpeedSection

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White100)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Settings",
            fontFamily = PlusJakartaSansFontFamily,
            fontWeight = FontWeight.Bold,
            color = Black100,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        LocationSection(color= HotPrimary)
        Spacer(modifier = Modifier.height(32.dp))
        TemperatureAndWindSpeedSection(color = HotPrimary)
        Spacer(modifier = Modifier.height(12.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    NaseemTheme {
        SettingsScreen()
    }
}