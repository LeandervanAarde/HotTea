package com.example.hottea.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Input(modifier: Modifier = Modifier, textValue: String, changedVal: (String) -> Unit, label: String, icon: ImageVector, keyboardOptions: KeyboardOptions, transformation: VisualTransformation ){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.White),
        value = textValue ,
        onValueChange = { changedVal(it) },
        leadingIcon = { androidx.compose.material3.Icon(imageVector = icon , contentDescription = null )} ,
        label = { Text (text = label) },
        keyboardOptions = keyboardOptions,
        visualTransformation = transformation
    )
}