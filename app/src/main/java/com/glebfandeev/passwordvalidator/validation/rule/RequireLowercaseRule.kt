package com.glebfandeev.passwordvalidator.validation.rule

import com.glebfandeev.passwordvalidator.validation.ValidationRule

class RequireLowercaseRule : ValidationRule {
    override fun validate(password: String) = password.any { it.isLowerCase() }
    override val errorMessage = "Пароль должен содержать хотя бы одну строчную букву."
}