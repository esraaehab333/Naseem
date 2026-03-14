package com.example.naseem.presentation.fav.components
import android.location.Address
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@Composable
fun SearchSection(
    searchQuery: String,
    suggestions: List<Address>,
    color: Color,
    onQueryChange: (String) -> Unit,
    onSuggestionClick: (Address) -> Unit
) {
    SearchTextField(
        searchQuery = searchQuery,
        onQueryChange = onQueryChange
    )

    if (suggestions.isNotEmpty() && searchQuery.isNotBlank()) {
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            suggestions.forEachIndexed { index, address ->
                SuggestionRow(
                    address = address,
                    color = color,
                    onClick = { onSuggestionClick(address) }
                )
                if (index < suggestions.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}