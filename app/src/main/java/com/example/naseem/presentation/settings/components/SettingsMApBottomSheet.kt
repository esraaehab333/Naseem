
import android.location.Address
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.R
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100
import org.osmdroid.util.GeoPoint

@Composable
fun SettingsMapBottomSheet(
    color: Color,
    selectedAddress: Address?,
    selectedLocation: GeoPoint?,
    onSaveClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = White100,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Handle indicator
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .background(color.copy(alpha = 0.3f), RoundedCornerShape(2.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // City / locality name (large)
            val cityName = selectedAddress?.locality
                ?: selectedAddress?.subAdminArea
                ?: stringResource(R.string.unknown_location)

            Text(
                text = cityName,
                fontFamily = PlusJakartaSansFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black100,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Full address line (smaller)
            val fullAddress = selectedAddress?.getAddressLine(0)
                ?: selectedLocation?.let { "${it.latitude}, ${it.longitude}" }
                ?: ""

            if (fullAddress.isNotBlank()) {
                Text(
                    text = fullAddress,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Gray100,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = color)
            ) {
                Text(
                    text = stringResource(R.string.save_location),
                    fontFamily = PlusJakartaSansFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = White100,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}