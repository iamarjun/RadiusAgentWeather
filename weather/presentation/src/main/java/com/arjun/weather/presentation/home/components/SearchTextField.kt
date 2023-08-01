package com.arjun.weather.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arjun.core_ui.LocalSpacing
import com.arjun.core_ui.theme.RadiusAgentSupportingGray
import com.arjun.core_ui.theme.RadiusAgentSupportingGray2
import com.arjun.weather.presentation.R

@Composable
fun SearchTextField(
    modifier: Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    hint: String = stringResource(id = R.string.hint),
    shouldShowHint: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
) {
    val spacing = LocalSpacing.current

    Box(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = text,
            modifier = modifier
                .clip(RoundedCornerShape(50))
                .padding(spacing.spaceExtraSmall)
                .background(RadiusAgentSupportingGray2)
                .fillMaxWidth()
                .padding(spacing.spaceMedium)
                .padding(start = spacing.spaceLarge)
                .onFocusChanged(onFocusChange),
            onValueChange = onValueChange,
            singleLine = true,
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                    defaultKeyboardAction(ImeAction.Search)
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Search
            ),
        )

        if (shouldShowHint) {
            Text(
                modifier = modifier
                    .align(Alignment.CenterStart)
                    .padding(start = spacing.spaceLarge + spacing.spaceMedium),
                text = hint,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Light,
                color = Color.LightGray,
            )
        }
        IconButton(
            modifier = modifier.align(Alignment.CenterStart),
            onClick = onSearch
        ) {
            Icon(
                modifier = modifier,
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = RadiusAgentSupportingGray
            )
        }
        if (text.isNotEmpty()) {
            IconButton(
                modifier = modifier.align(Alignment.CenterEnd),
                onClick = onClear
            ) {
                Icon(
                    modifier = modifier,
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Clear Search Result",
                    tint = RadiusAgentSupportingGray
                )
            }
        }
    }

}