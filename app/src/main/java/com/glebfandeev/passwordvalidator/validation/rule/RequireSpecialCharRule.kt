package com.glebfandeev.passwordvalidator.validation.rule

import com.glebfandeev.passwordvalidator.validation.ValidationRule

class RequireSpecialCharRule(private val specialChars: String) : ValidationRule {
    override fun validate(password: String) = password.any { it in specialChars }
    override val errorMessage = "Пароль должен содержать хотя бы один специальный символ из: $specialChars"
}