package com.example.naseem.presentation.fav.components

import ButtonRow
import android.location.Address
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import org.osmdroid.util.GeoPoint

@Composable
fun BottomSheetSection(
    color: Color,
    selectedAddress: Address?,
    selectedLocation: GeoPoint?,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color.White.copy(alpha = 0.97f))
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(40.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color.Gray.copy(alpha = 0.4f))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_location_soild),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(26.dp)
            )
            Column {
                Text(
                    text = selectedAddress?.locality
                        ?: selectedAddress?.subAdminArea
                        ?: "Unknown Location",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Black100,
                    fontFamily = PlusJakartaSansFontFamily
                )
                Spacer(modifier = Modifier.height(5.dp) )
                Text(
                    text = selectedAddress?.getAddressLine(0)
                        ?: selectedLocation?.let {
                            "%.4f, %.4f".format(it.latitude, it.longitude)
                        } ?: "",
                    fontSize = 13.sp,
                    color = Gray100,
                    maxLines = 2,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = Gray100.copy(alpha = 0.15f))
        Spacer(modifier = Modifier.height(12.dp))
        //TODO: not only the temp
        SelectedLocationWeather(color)
        Spacer(modifier = Modifier.height(30.dp))
        ButtonRow(
            color = color,
            onSaveClick = onSaveClick
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}