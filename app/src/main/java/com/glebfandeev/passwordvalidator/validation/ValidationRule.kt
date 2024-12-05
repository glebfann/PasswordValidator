package com.glebfandeev.passwordvalidator.validation

interface ValidationRule {
    fun validate(password: String): Boolean
    val errorMessage: String
}