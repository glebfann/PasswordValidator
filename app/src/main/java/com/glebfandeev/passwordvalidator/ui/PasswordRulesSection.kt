package com.glebfandeev.passwordvalidator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PasswordRulesSection(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Минимальная длина пароля (символов):", modifier = Modifier.weight(1f))
            OutlinedTextField(
                value = if (viewModel.minLength.intValue == 0) "" else viewModel.minLength.intValue.toString(),
                onValueChange = { newValue ->
                    if (newValue.isEmpty()) {
                        viewModel.minLength.intValue = 0
                    }
                    newValue.toIntOrNull()?.let { intValue ->
                        if (intValue > 0) {
                            viewModel.minLength.intValue = intValue
                        }
                    }
                    viewModel.updateRules()
                },
                modifier = Modifier.width(80.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SwitchWithLabel(
            label = "Требовать наличие заглавных букв",
            checked = viewModel.requireUppercase.value,
            onCheckedChange = {
                viewModel.requireUppercase.value = it
                viewModel.updateRules()
            }
        )

        SwitchWithLabel(
            label = "Требовать наличие строчных букв",
            checked =


            viewModel.requireLowercase.value,
            onCheckedChange = {
                viewModel.requireLowercase.value = it
                viewModel.updateRules()
            }
        )

        SwitchWithLabel(
            label = "Требовать наличие цифр",
            checked = viewModel.requireDigit.value,
            onCheckedChange = {
                viewModel.requireDigit.value = it
                viewModel.updateRules()
            }
        )

        // Требовать специальные символы
        SwitchWithLabel(
            label = "Требовать наличие специальных символов",
            checked = viewModel.requireSpecialChar.value,
            onCheckedChange = {
                viewModel.requireSpecialChar.value = it
                viewModel.updateRules()
            }
        )
        if (viewModel.requireSpecialChar.value) {
            OutlinedTextField(
                value = viewModel.specialChars.value,
                onValueChange = {
                    viewModel.specialChars.value = it
                    viewModel.updateRules()
                },
                label = { Text("Специальные символы") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        SwitchWithLabel(
            label = "Запретить использование определенных символов",
            checked = viewModel.excludeSymbols.value,
            onCheckedChange = {
                viewModel.excludeSymbols.value = it
                viewModel.updateRules()
            }
        )
        if (viewModel.excludeSymbols.value) {
            OutlinedTextField(
                value = viewModel.forbiddenChars.value,
                onValueChange = {
                    viewModel.forbiddenChars.value = it
                    viewModel.updateRules()
                },
                label = { Text("Запрещенные символы") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        SwitchWithLabel(
            label = "Использовать словарь слабых паролей",
            checked = viewModel.useWeakPasswords.value,
            onCheckedChange = {
                viewModel.useWeakPasswords.value = it
                viewModel.updateRules()
            }
        )
        if (viewModel.useWeakPasswords.value) {
            WeakPasswordsSection(viewModel)
        }
    }
}

@Composable
private fun SwitchWithLabel(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
