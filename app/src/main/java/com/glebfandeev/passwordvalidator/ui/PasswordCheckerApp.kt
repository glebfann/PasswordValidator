package com.glebfandeev.passwordvalidator.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordCheckerApp(viewModel: MainViewModel) {
    val password by viewModel.password.collectAsState()
    val isPasswordValid by viewModel.isPasswordValid.collectAsState()
    val errorMessages by viewModel.errorMessages.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Проверка пароля") }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = password,
                onValueChange = {
                    viewModel.onPasswordChange(it)
                },
                label = { Text("Введите пароль") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            isPasswordValid?.let { isValid ->
                if (isValid) {
                    Text("Пароль надежный ✅", color = MaterialTheme.colorScheme.primary)
                } else {
                    Text("Пароль ненадежный ❌", color = MaterialTheme.colorScheme.error)
                    errorMessages.forEach { error ->
                        Text("• $error", color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Настройки проверки пароля:",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            PasswordRulesSection(viewModel)
        }
    }
}
