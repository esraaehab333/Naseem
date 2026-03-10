package com.example.naseem.presentation.settings.view
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.NaseemTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.room.util.copy
import com.example.naseem.R
import com.example.naseem.presentation.settings.components.LanguageSection
import com.example.naseem.presentation.settings.components.LocationSection
import com.example.naseem.presentation.settings.components.SettingsRow
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.HotPrimary
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100
import com.example.naseem.presentation.settings.components.TemperatureAndWindSpeedSection
import com.example.naseem.presentation.settings.components.UnitSection
import com.example.naseem.ui.theme.Gray100

@Composable
fun SettingsScreen(color:Color) {
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
        LocationSection(color= color)
        Spacer(modifier = Modifier.height(32.dp))
        TemperatureAndWindSpeedSection(color = color)
        Spacer(modifier = Modifier.height(32.dp))
        LanguageSection(color = color)
    }
}