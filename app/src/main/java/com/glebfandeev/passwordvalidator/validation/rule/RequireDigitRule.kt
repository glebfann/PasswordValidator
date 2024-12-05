package com.glebfandeev.passwordvalidator.validation.rule

import com.glebfandeev.passwordvalidator.validation.ValidationRule

class RequireDigitRule : ValidationRule {
    override fun validate(password: String) = password.any { it.isDigit() }
    override val errorMessage = "Пароль должен содержать хотя бы одну цифру."
}