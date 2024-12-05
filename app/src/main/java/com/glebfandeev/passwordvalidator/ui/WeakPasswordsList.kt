package com.glebfandeev.passwordvalidator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeakPasswordsList(
    weakPasswords: List<String>,
    onUpdate: (List<String>) -> Unit,
    onSaveToFile: () -> Unit
) {
    var newPassword by remember { mutableStateOf("") }
    var passwordsList by remember { mutableStateOf(weakPasswords.toMutableList()) }

    Column {
        passwordsList.forEach { password ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(password)
                IconButton(onClick = {
                    passwordsList.remove(password)
                    onUpdate(passwordsList)
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Удалить")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("Новый слабый пароль") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newPassword.isNotEmpty()) {
                    passwordsList.add(newPassword)
                    newPassword = ""
                    onUpdate(passwordsList)
                }
            }) {
                Text("Добавить")
            }
        }

        Button(onClick = { onSaveToFile() }, modifier = Modifier.padding(top = 16.dp)) {
            Icon(Icons.Default.Done, contentDescription = "Сохранить")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Сохранить")
        }
    }
}
