package com.glebfandeev.passwordvalidator.validation.rule

import com.glebfandeev.passwordvalidator.validation.ValidationRule

class MinLengthRule(private val minLength: Int) : ValidationRule {
    override fun validate(password: String) = password.length >= minLength
    override val errorMessage = "Пароль должен быть не короче $minLength символов."
}