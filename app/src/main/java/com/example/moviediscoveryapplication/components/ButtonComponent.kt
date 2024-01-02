package com.example.moviediscoveryapplication.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundedButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    shape: RoundedCornerShape = RoundedCornerShape(50.dp),
    filled: Boolean = true,
    elevation: Dp = 0.dp
) {
    val colors = if (filled) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = Color.White
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.tertiary
        )
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation)
            .clip(shape),
        colors = colors,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 18.dp),
        shape = shape,
        border = if (!filled) BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary) else null,
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = colors.contentColor,
            textAlign = TextAlign.Center
        )
    }
}