package com.abhi.classconnect.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhi.classconnect.R
import com.abhi.classconnect.utils.NO_LESSONS


@Preview
@Composable
internal fun ShowMessage(
    message: String = NO_LESSONS
) {
    Box(
        modifier =
            Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = message,
            fontSize = 25.sp
        )
    }
}

@Composable
internal fun FloatingButton(
    onCLick : () -> Unit
){
    ExtendedFloatingActionButton(
        onClick = {
           onCLick()
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.check_in_out_icon),
                contentDescription = "Add Lesson"
            )
        },
        text = { Text("Add Lesson") }
    )
}



@Composable
internal fun ClassRoomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeHolder: String,
    singleLine: Boolean,
    keyBoardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) },
        placeholder = { Text(placeHolder) },
        singleLine = singleLine,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType)
    )
}

