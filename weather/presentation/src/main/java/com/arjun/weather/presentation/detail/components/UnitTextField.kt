package com.arjun.weather.presentation.detail.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arjun.core_ui.LocalSpacing

@Composable
fun UnitText(
    modifier: Modifier,
    value: String,
    unit: String,
    textStyle: TextStyle = MaterialTheme.typography.displayLarge.copy(
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
        fontSize = 70.sp
    )
) {
    val spacing = LocalSpacing.current
    Row(modifier = modifier) {
        Text(
            text = value,
            modifier = modifier
                .width(IntrinsicSize.Min),
            style = textStyle
        )
        Spacer(modifier = modifier.width(spacing.spaceSmall))
        Text(
            text = unit,
            modifier = modifier,
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
            )
        )
    }
}