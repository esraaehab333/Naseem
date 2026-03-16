import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.Gray100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily

@Composable
fun TimeBox(
    label: String,
    time: String,
    amPm: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontFamily = PlusJakartaSansFontFamily,
            fontWeight = FontWeight.Normal,
            color = Gray100,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(16.dp),
            color = Gray100.copy(0.05f),
            border = BorderStroke(1.dp,Gray100.copy(0.1f),)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = time,
                    fontSize = 16.sp,
                    fontFamily = PlusJakartaSansFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF111827)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = amPm,
                        fontSize = 10.sp,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                }
            }
        }
    }
}