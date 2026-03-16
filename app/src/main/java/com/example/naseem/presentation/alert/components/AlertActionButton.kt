import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naseem.ui.theme.Black100
import com.example.naseem.ui.theme.PlusJakartaSansFontFamily
import com.example.naseem.ui.theme.White100

@Composable
fun AlertActionButton(color: Color){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = {
                //todo: edit alert
            },
            modifier = Modifier.weight(1f).height(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F4F9)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Edit Rules",
                color = Black100,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 12.sp
            )
        }
        Button(
            onClick = {
                //todo:switch
            },
            modifier = Modifier.weight(1f).height(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = color),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Delete",
                color = White100,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 12.sp
            )
        }
    }
}