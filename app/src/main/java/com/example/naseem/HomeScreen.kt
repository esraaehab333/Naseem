package com.example.naseem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.ColdPrimary
import com.example.naseem.ui.theme.NaseemTheme

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.white))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 20.dp,
                        bottomStart = 20.dp
                    )
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.extremecold),
                contentDescription = "Header Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row {
                    Icon(
                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = null,
                        tint = colorResource(R.color.black)
                    )
                    Text(
                        text = "San Francisco",
                        color = colorResource(R.color.black),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }
                Text(
                    text = "19°",
                    color = colorResource(R.color.black),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 96.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )
                Text(
                    text = "Today , 2 FEB 2026",
                    color = colorResource(R.color.black),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center)
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .padding(vertical = 18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherDetailItem(
                    iconRes = R.drawable.ic_humidity,
                    label = "Humidity",
                    value = "85%",
                    color = ColdPrimary
                )
                WeatherDetailItem(
                    iconRes = R.drawable.ic_wind,
                    label = "Wind",
                    value = "9 km/h",
                    color = ColdPrimary
                )
                WeatherDetailItem(
                    iconRes = R.drawable.ic_cloudy,
                    label = "Cloudy",
                    value = "Overcast",
                    color = ColdPrimary
                )
                WeatherDetailItem(
                    iconRes = R.drawable.ic_pressure,
                    label = "Humidity",
                    value = "85%",
                    color = ColdPrimary
                )
            }
        }
    }
}
@Composable
fun WeatherDetailItem(iconRes: Int, label: String, value: String , color:Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = Color(0xFF7B8794),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = Color(0xFF1F2D3D), 
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NaseemTheme {
        HomeScreen()
    }
}