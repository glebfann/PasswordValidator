package com.glebfandeev.passwordvalidator.validation.rule

import com.glebfandeev.passwordvalidator.validation.ValidationRule

class ExcludeSymbolsRule(private val forbiddenChars: String) : ValidationRule {
    override fun validate(password: String) = password.none { it in forbiddenChars }
    override val errorMessage = "Пароль не должен содержать символы: $forbiddenChars"
}