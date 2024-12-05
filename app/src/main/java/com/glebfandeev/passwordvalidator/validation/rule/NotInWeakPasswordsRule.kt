package com.glebfandeev.passwordvalidator.validation.rule

import com.glebfandeev.passwordvalidator.validation.ValidationRule

class NotInWeakPasswordsRule(private val weakPasswords: List<String>) : ValidationRule {
    override fun validate(password: String) = password !in weakPasswords
    override val errorMessage = "Пароль найден в списке слабых паролей."
}