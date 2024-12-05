package com.glebfandeev.passwordvalidator.ui

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.glebfandeev.passwordvalidator.validation.PasswordValidator
import com.glebfandeev.passwordvalidator.validation.rule.ExcludeSymbolsRule
import com.glebfandeev.passwordvalidator.validation.rule.MinLengthRule
import com.glebfandeev.passwordvalidator.validation.rule.NotInWeakPasswordsRule
import com.glebfandeev.passwordvalidator.validation.rule.RequireDigitRule
import com.glebfandeev.passwordvalidator.validation.rule.RequireLowercaseRule
import com.glebfandeev.passwordvalidator.validation.rule.RequireSpecialCharRule
import com.glebfandeev.passwordvalidator.validation.rule.RequireUppercaseRule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.logging.Level
import java.util.logging.Logger

class MainViewModel : ViewModel() {
    var minLength = mutableStateOf(8)
    var requireUppercase = mutableStateOf(false)
    var requireLowercase = mutableStateOf(false)
    var requireDigit = mutableStateOf(false)
    var requireSpecialChar = mutableStateOf(false)
    var specialChars = mutableStateOf("!@#$%^&*")
    var excludeSymbols = mutableStateOf(false)
    var forbiddenChars = mutableStateOf("<>{}[]")
    var useWeakPasswords = mutableStateOf(false)

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isPasswordValid = MutableStateFlow<Boolean?>(null)
    val isPasswordValid = _isPasswordValid.asStateFlow()

    private val _errorMessages = MutableStateFlow<List<String>>(emptyList())
    val errorMessages = _errorMessages.asStateFlow()

    private val _weakPasswords = MutableStateFlow<List<String>>(emptyList())
    val weakPasswords = _weakPasswords.asStateFlow()

    private val _selectedFileUri = MutableStateFlow<Uri?>(null)
    val selectedFileUri = _selectedFileUri.asStateFlow()

    private val validator = PasswordValidator(rules = listOf(MinLengthRule(minLength.value)))
    private val logger = Logger.getGlobal()

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        validatePassword()
    }

    fun updateRules() {
        val rules = listOfNotNull(
            MinLengthRule(minLength.value),
            RequireUppercaseRule().takeIf { requireUppercase.value },
            RequireLowercaseRule().takeIf { requireLowercase.value },
            RequireDigitRule().takeIf { requireDigit.value },
            RequireSpecialCharRule(specialChars.value).takeIf { requireSpecialChar.value },
            ExcludeSymbolsRule(forbiddenChars.value).takeIf { excludeSymbols.value },
            NotInWeakPasswordsRule(_weakPasswords.value).takeIf { useWeakPasswords.value }
        )
        validator.update(rules = rules)
        logger.log(Level.INFO, "Rules updated")
        validatePassword()
    }

    private fun validatePassword() {
        val (isValid, errors) = validator.validate(_password.value)
        _isPasswordValid.value = isValid
        _errorMessages.value = errors
    }

    fun selectFile(uri: Uri, context: Context) {
        _selectedFileUri.value = uri
        loadWeakPasswordsFromFile(uri, context)
    }

    private fun loadWeakPasswordsFromFile(uri: Uri, context: Context) {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.let {
            val reader = BufferedReader(InputStreamReader(it))
            val passwords = reader.readLines()
            _weakPasswords.value = passwords
            logger.log(Level.INFO, "Passwords loaded: $passwords")
            updateRules()
        }
    }

    fun updateWeakPasswords(newList: List<String>) {
        _weakPasswords.value = newList
        updateRules()
    }

    fun saveWeakPasswordsToFile(context: Context) {
        _selectedFileUri.value?.let { uri ->
            try {
                val contentResolver = context.contentResolver
                val outputStream = contentResolver.openOutputStream(uri, "w")
                outputStream?.let {
                    val writer = OutputStreamWriter(it)
                    _weakPasswords.value.forEach { password ->
                        writer.write(password + "\n")
                    }
                    writer.flush()
                    writer.close()
                }
            } catch (e: Exception) {
                logger.log(Level.SEVERE, "Save passwords failed")
            }
        }
    }
}