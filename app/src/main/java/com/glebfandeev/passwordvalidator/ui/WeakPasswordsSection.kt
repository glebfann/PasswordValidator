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

    Column {
        Button(onClick = { openFileLauncher.launch(arrayOf("text/plain")) }) {
            Text("Выбрать файл слабых паролей")
        }


        viewModel.selectedFileUri.value?.let { uri ->
            Text("Выбранный файл: ${uri.path}")
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
