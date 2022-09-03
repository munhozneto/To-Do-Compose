package com.pmn.to_docompose.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.pmn.to_docompose.R

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialogClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirmClick()
                        closeDialogClick()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.yes_label)
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        closeDialogClick()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.no_label)
                    )
                }
            },
            onDismissRequest = {
                closeDialogClick()
            }
        )
    }
}