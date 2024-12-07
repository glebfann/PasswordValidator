package com.glebfandeev.passwordvalidator.ui

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun WeakPasswordsSection(viewModel: MainViewModel) {
    val context = LocalContext.current

    val openFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                viewModel.selectFile(it, context)
            }
        }
    )

    val createFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain"),
        onResult = { uri ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                viewModel.selectFile(it, context)
            }
        }
    )

    Column {
        Button(
            onClick = { openFileLauncher.launch(arrayOf("text/plain")) }
        ) {
            Text("Открыть существующий файл")
        }

        Button(
            onClick = { createFileLauncher.launch("weak_passwords.txt") }
        ) {
            Text("Создать новый файл")
        }

        viewModel.selectedFileUri.let { uri ->
            if (uri.value != null) {
                Text("Выбранный файл: ${uri.value?.path}")
            } else {
                Text("Выберите файл или добавьте слабые пароли вручную ниже.")
            }
            Spacer(modifier = Modifier.height(8.dp))
            WeakPasswordsList(
                weakPasswords = viewModel.weakPasswords.collectAsState().value,
                onUpdate = { updatedList ->
                    viewModel.updateWeakPasswords(updatedList)
                    viewModel.updateRules()
                },
                onSaveToFile = { viewModel.saveWeakPasswordsToFile(context) }
            )
        }
    }
}