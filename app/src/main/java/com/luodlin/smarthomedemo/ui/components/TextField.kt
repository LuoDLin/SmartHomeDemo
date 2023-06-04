package com.luodlin.smarthomedemo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.ldl.ouc_iot.ui.components.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    label: String,
    maxLength: Int,
    filter: (Char) -> Boolean = { true },
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = textFieldState.text,
        onValueChange = {
            textFieldState.text = it.take(maxLength).filter(filter)
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { textFieldState.onFocusChange(it.isFocused) },
        textStyle = MaterialTheme.typography.bodyLarge,
        label = { Text(text = label, style = MaterialTheme.typography.labelLarge) },
        trailingIcon = trailingIcon,
        singleLine = true,
        visualTransformation = visualTransformation,
        isError = textFieldState.isError,
        supportingText = {
//            uiState.getError()?.let { TextFieldError(textError = it) }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction, keyboardType = keyboardType
        ),
        keyboardActions = keyboardActions,
    )
}