package com.glebfandeev.passwordvalidator.validation.rule

import com.glebfandeev.passwordvalidator.validation.ValidationRule

class RequireUppercaseRule : ValidationRule {
    override fun validate(password: String) = password.any { it.isUpperCase() }
    override val errorMessage = "Пароль должен содержать хотя бы одну заглавную букву."
}